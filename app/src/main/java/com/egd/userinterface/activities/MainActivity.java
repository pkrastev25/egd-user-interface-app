
package com.egd.userinterface.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.egd.userinterface.controllers.BluetoothDistanceDetector;

import com.egd.userinterface.R;
import com.egd.userinterface.tests.TestCases;


/*
 * Entry point for the application.
*/
public class MainActivity extends AppCompatActivity {
   private  BluetoothDistanceDetector bluetoothDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bluetoothDetector = new BluetoothDistanceDetector();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TestCases.GPIOUtilOutputTest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bluetoothDetector.init(this);
//        TestCases.TextToSpeechControllerTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TestCases.closeGPIOPorts();
    }
}
