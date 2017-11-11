package com.egd.userinterface.controllers;

/**
 * Created by Evgeniy Bond on 11.11.2017.
 */

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothProfile;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;

        import java.util.Timer;
        import java.util.TimerTask;


public class BluetoothDistanceDetector
{
    private static final String ADAPTER_FRIENDLY_NAME = "My bluetooth device";
    private static final String TAG = "BlutoothDistanceDetector";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothProfile mA2DPSinkProxy;

    //during connection estblishing
    private long bluetoothDiscoveryDelay = 4000; //ms
    //for checking if devices is connected or in range
    private long bluetoothDiscoveryPeriod = 20000; //ms

    private int SIGNAL_STRENGTH_THRESHOLD = 99999;

    private Timer bluetoothTimer = new Timer();

    private TimerTask bluetoothSearch = new TimerTask() {
    synchronized public void run() {
        mBluetoothAdapter.startDiscovery();
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
    }

    public void init() {

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

        bluetoothTimer.scheduleAtFixedRate(bluetoothSearch, bluetoothDiscoveryDelay, bluetoothDiscoveryPeriod);
    }

    // launch discovery every 10 seconds or so



    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                //should check name here
                Log.i(TAG, "Signal strength:" + rssi);
                if (rssi<SIGNAL_STRENGTH_THRESHOLD){
                    // here we should notify user
                }
            }
        }
    };
}

