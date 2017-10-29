package com.egd.userinterface.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.egd.userinterface.R;
import com.egd.userinterface.tests.TestCases;

/**
 * Entry point for the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TestCases.GPIOUtilOutputTest();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //TestCases.TextToSpeechControllerTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TestCases.closeGPIOPorts();
    }
}
