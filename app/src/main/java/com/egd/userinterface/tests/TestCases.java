package com.egd.userinterface.tests;

import android.os.Handler;
import android.util.Log;

import com.egd.userinterface.controllers.LEDController;
import com.egd.userinterface.controllers.MotorController;
import com.egd.userinterface.controllers.SpeechToTextController;
import com.egd.userinterface.controllers.TextToSpeechController;

import java.util.concurrent.TimeUnit;

/**
 * Contains test cases for all modules in this application.
 *
 * @author Petar Krastev
 * @since 29.10.2017
 */
public class TestCases {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = TestCases.class.getSimpleName();

    /**
     * Simple test case for the {@link TextToSpeechController}.
     */
    public static void TextToSpeechControllerTest() {
        Log.i(TAG, "TestCases.TextToSpeechControllerTest() call");
        TextToSpeechController.getInstance().speak("Hello, this is the text to speech feature from Android");
        TextToSpeechController.getInstance().speak("If you are hearing this, it successfully worked");
        Log.i(TAG, "TestCases.TextToSpeechControllerTest() last call");
    }

    /**
     * Simple test case for the {@link LEDController}. Turns on the LEDs and
     * turns them off after 30 seconds.
     */
    public static void LEDControllerTest() {
        Log.i(TAG, "TestCases.LEDControllerTest() call");
        LEDController.getInstance().LEDsON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LEDController.getInstance().LEDsOFF();
                Log.i(TAG, "TestCases.LEDControllerTest() last call");
            }
        }, TimeUnit.SECONDS.toMillis(30));
    }

    /**
     * Simple test case for the {@link MotorController}. Starts the motor
     * and stops it after 1 second.
     */
    public static void MotorControllerTest() {
        Log.i(TAG, "TestCases.MotorControllerTest() call");
        MotorController.getInstance().start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MotorController.getInstance().stop();
                Log.i(TAG, "TestCases.MotorControllerTest() last call");
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * Simple test case for the {@link SpeechToTextController}. Triggers a speech
     * recognition and forwards the result to the {@link TextToSpeechController}.
     */
    public static void SpeechToTextControllerTest() {
        Log.i(TAG, "TestCases.SpeechToTextControllerTest() call");
        SpeechToTextController.getInstance().recognizeSpeech();
    }
}
