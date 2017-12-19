package com.egd.userinterface.controllers;
/**
 * Created by Evgeniy Bond on 19.12.2017.
 */
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.le.*;
        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import android.os.AsyncTask;

        import java.util.Set;
        import java.util.Timer;
        import java.util.TimerTask;
        import com.egd.userinterface.controllers.TextToSpeechController;
        import com.egd.userinterface.controllers.models.ITextToSpeechController;

public class BluetoothDistanceDetector
{
    private static final String ADAPTER_FRIENDLY_NAME = "My Raspberry EGD Device";
    private static final String TAG = "BluetoothDistanceDetector";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothScanner;
    private ITextToSpeechController ttsModule;

    //during connection establishing
    private long bluetoothDiscoveryDelay = 4000; //ms
    //for checking if devices is connected or in range
    private long bluetoothDiscoveryPeriod = 20000; //ms

    private int SIGNAL_STRENGTH_THRESHOLD = -90;
    //signal strength of -90 dBm is around 3-4 meters
    //(checked experimentally by putting device in different distance from Raspberry module)

    private String PairedDeviceAddress = "EB:D4:89:BC:B2:9D"; //see comments a line below
//    Even though program recognizes the fact EGD was paired with
//    smartphone, it's not able to get the name of last paired device
//    due to bluetooth adapter limitations.
//    That's why there's a forcibly inserted device's address to check if
//    the program checks the signal strength correctly
//    and sends corresponding messages.

    private Timer bluetoothTimer;

    private boolean isDeviceFound = true;

    // ------- This commented part is no more needed -------
    //searching and pairing devices
    //private Set<BluetoothDevice> pairedDevices;

    //scanning devices (with Bluetooth) around
    private ScanCallback BLE_Callback;
    private void initScanCallback()
    {
        BLE_Callback = new ScanCallback()
        {
            @Override
            public void onScanResult(int callbackType, ScanResult result)
            {

                if (result.getDevice().getAddress().equals(PairedDeviceAddress))
                {
                    //names are mostly null, so the most important thing is address
                    Log.i(TAG, "Device Address: " + result.getDevice().getAddress() + " Device Name: " + result.getDevice().getName() + " rssi: " + result.getRssi() + "\n");
                    if (result.getRssi() > SIGNAL_STRENGTH_THRESHOLD)
                    {
                        isDeviceFound = true;
                    }
                }
            }
        };
    }

    //checking if the device is found
    //and making EGD to notify user about the current status
    private void checkConnectionStatus()
    {
        if(!isDeviceFound)
        {
            Log.i(TAG, "You have forgotten me!");
            ttsModule.speak("You have forgotten the device");
        }
        else
        {
            Log.i(TAG, "Now we are connected!");
        }
        isDeviceFound=false;
    }

    private TimerTask bluetoothSearch;

    private void initTimerTask()
    {
        bluetoothSearch = new TimerTask()
        {
            synchronized public void run()
            {
                checkConnectionStatus();
                // pairedDevices= mBluetoothAdapter.getBondedDevices();
                AsyncTask.execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mBluetoothScanner.startScan(BLE_Callback);
                    }
                });
            }

        };

    }

    /**
     * Initiate the A2DP sink.
     */
    private void initA2DPSink()
    {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Log.e(TAG, "Bluetooth adapter not available or not enabled.");
            return;
        }
        Log.d(TAG, "Set up Bluetooth Adapter name and profile");
        mBluetoothAdapter.setName(ADAPTER_FRIENDLY_NAME);
    }

    //---- Not needed anymore, but will leave it here just in case anyone would need it------
//    //discovering devices with bluetooth
//    private void enableDiscoverable(Context localContext) {
//        Log.d(TAG, "Registering for discovery.");
//        Intent discoverableIntent =
//                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
//                300);
//        localContext.startActivity(discoverableIntent);
//    }

    //enabling Bluetooth adapter
    public void init(Context localContext)
    {
        TextToSpeechController.init(localContext);
        ttsModule = TextToSpeechController.getInstance();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            Log.w(TAG, "No default Bluetooth adapter. Device likely does not support bluetooth.");
            return;
        }

        if (mBluetoothAdapter.isEnabled())
        {
            Log.d(TAG, "Bluetooth Adapter is already enabled.");
            initA2DPSink();
        }
        else
        {
            Log.d(TAG, "Bluetooth adapter not enabled. Enabling.");
            mBluetoothAdapter.enable();
        }

        mBluetoothScanner = mBluetoothAdapter.getBluetoothLeScanner();

        bluetoothTimer = new Timer();
        initTimerTask();
        bluetoothTimer.scheduleAtFixedRate(bluetoothSearch, bluetoothDiscoveryDelay, bluetoothDiscoveryPeriod);
    }

}

