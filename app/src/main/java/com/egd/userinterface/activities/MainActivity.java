package com.egd.userinterface.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.egd.userinterface.R;
import com.egd.userinterface.controllers.LEDController;
import com.egd.userinterface.controllers.MotorController;
import com.egd.userinterface.controllers.SpeechToTextController;
import com.egd.userinterface.controllers.TextToSpeechController;
import com.egd.userinterface.tests.TestCases;

/**
 * Entry point for the application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TestCases.LEDControllerTest();
        TestCases.MotorControllerTest();
        TestCases.TextToSpeechControllerTest();
        TestCases.SpeechToTextControllerTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LEDController.getInstance().clean();
        MotorController.getInstance().clean();
        TextToSpeechController.getInstance().clean();
        SpeechToTextController.getInstance().clean();
    }
}
