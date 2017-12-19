package com.egd.userinterface.controllers;

/**
 * Created by Evgeniy Bond on 11.11.2017.
 */

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothProfile;
        import android.bluetooth.le.*;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.util.Log;
        import android.os.AsyncTask;

        import java.util.Set;
        import java.util.Timer;
        import java.util.TimerTask;


public class BluetoothDistanceDetector
{
    private static final String ADAPTER_FRIENDLY_NAME = "My Raspberry EGD Device";
    private static final String TAG = "BlutoothDistanceDetector";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothScanner;
    private Context localContext;

    //during connection estblishing
    private long bluetoothDiscoveryDelay = 4000; //ms
    //for checking if devices is connected or in range
    private long bluetoothDiscoveryPeriod = 20000; //ms

    private int SIGNAL_STRENGTH_THRESHOLD = -90;
    private String PairedDeviceAddress = "EB:D4:89:BC:B2:9D";

    private Timer bluetoothTimer = new Timer();

    private boolean isDeviceFound = true;

    //searching and pairing devices
    private Set<BluetoothDevice> pairedDevices;

    private ScanCallback BLE_Callback = new ScanCallback() {


        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            if(result.getDevice().getAddress().equals(PairedDeviceAddress)){
                Log.i(TAG,"Device Address: " + result.getDevice().getAddress() + " Device Name: " + result.getDevice().getName() +  " rssi: " + result.getRssi() + "\n");
                if(result.getRssi()>SIGNAL_STRENGTH_THRESHOLD){
                    isDeviceFound=true;
                }
            }
        }
    };


    //Log.i(TAG, "Now we are together again!");
    //Log.i(TAG, "Found owner by address!");

    private void checkConnectionStatus(){
        if(!isDeviceFound){
            Log.i(TAG, "You have forgotten me!");
        } else {
            Log.i(TAG, "Now we are connected!");
        }
        isDeviceFound=false;
    }

    private TimerTask bluetoothSearch = new TimerTask() {
    synchronized public void run() {

        checkConnectionStatus();
       // pairedDevices= mBluetoothAdapter.getBondedDevices();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mBluetoothScanner.startScan(BLE_Callback);
            }
        });

//        for(BluetoothDevice bd : pairedDevices){
//
//            String a = bd.getName();
//            if (a!=null) {
//                Log.i(TAG,bd.getName());
//
//            }
//            Log.i(TAG,bd.getAddress());
//        }

        //mBluetoothScanner.stopScan(BLE_Callback);
       // mBluetoothScanner.startScan(BLE_Callback);
//        if (mBluetoothAdapter.isDiscovering()) {
//        //    mBluetoothAdapter.cancelDiscovery();
//        } else {
//            mBluetoothAdapter.startDiscovery();
//        }
    }

};

    /**
     * Initiate the A2DP sink.
     */
    private void initA2DPSink() {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Log.e(TAG, "Bluetooth adapter not available or not enabled.");
            return;
        }
        Log.d(TAG, "Set up Bluetooth Adapter name and profile");
        mBluetoothAdapter.setName(ADAPTER_FRIENDLY_NAME);
//        mBluetoothAdapter.getProfileProxy(localContext, new BluetoothProfile.ServiceListener() {
//            @Override
//            public void onServiceConnected(int profile, BluetoothProfile proxy) {
//                enableDiscoverable(localContext);
//            }
//            @Override
//            public void onServiceDisconnected(int profile) {
//            }
//        }, 11);
    }

    //discovering devices with bluetooth
    private void enableDiscoverable(Context localContext) {
        Log.d(TAG, "Registering for discovery.");
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                300);
        localContext.startActivity(discoverableIntent);
    }

    public void init(Context newContext) {
        this.localContext = newContext;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.w(TAG, "No default Bluetooth adapter. Device likely does not support bluetooth.");
            return;
        }

        if (mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "Bluetooth Adapter is already enabled.");
            initA2DPSink();
        } else {
            Log.d(TAG, "Bluetooth adapter not enabled. Enabling.");
            mBluetoothAdapter.enable();
        }

//        BluetoothReceiver blReceiver = new BluetoothReceiver();
        mBluetoothScanner = mBluetoothAdapter.getBluetoothLeScanner();
//        localContext.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));


        bluetoothTimer.scheduleAtFixedRate(bluetoothSearch, bluetoothDiscoveryDelay, bluetoothDiscoveryPeriod);
    }



}

