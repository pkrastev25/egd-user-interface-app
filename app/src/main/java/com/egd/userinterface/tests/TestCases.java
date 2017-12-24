package com.egd.userinterface.tests;

import android.os.Handler;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum;
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
     * Simple test case for the {@link TextToSpeechController}.
     */
    public static void TextToSpeechControllerTest() {
        TextToSpeechController.getInstance().speak("Hello, this is the text to speech feature from Android");
        TextToSpeechController.getInstance().speak("If you are hearing this, it successfully worked");
    }

    /**
     * Simple test case for the {@link LEDController}. Turns on the LEDs and
     * turns them off after 30 seconds.
     */
    public static void LEDControllerTest() {
        final LEDController controller = new LEDController(
                Constants.LED_GPIO_INPUT,
                Constants.LED_GPIO_OUTPUT
        );

        controller.LEDsON();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                controller.LEDsOFF();
                controller.clean();
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * Simple test case for the {@link MotorController}. Starts the motor
     * and stops it after 1 second.
     */
    public static void MotorControllerTest() {
        MotorController.getInstance().start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MotorController.getInstance().stop();
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }

    /**
     * Simple test case for the {@link SpeechToTextController}. Triggers a speech
     * recognition and forwards the result to the {@link TextToSpeechController}.
     */
    public static void SpeechToTextControllerTest() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SpeechToTextController.getInstance().recognizeSpeech(SpeechRecognitionTypesEnum.ALL_KEYWORDS);
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }
}
