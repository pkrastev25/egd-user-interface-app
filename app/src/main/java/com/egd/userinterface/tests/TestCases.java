package com.egd.userinterface.tests;

import android.content.Context;
import android.os.Handler;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.controllers.LEDController;
import com.egd.userinterface.controllers.MotorController;
import com.egd.userinterface.controllers.TextToSpeechController;
import com.egd.userinterface.controllers.models.ILEDController;
import com.egd.userinterface.controllers.models.IMotorController;

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
     * turns them off after 1 minute.
     */
    public static void LEDControllerTest(Context context) {
        final ILEDController controller = new LEDController(
                context,
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
     * and stops it after 1 minute.
     */
    public static void MotorControllerTest(Context context) {
        final IMotorController controller = new MotorController(
                context,
                Constants.MOTOR_GPIO_INPUT,
                Constants.MOTOR_GPIO_OUTPUT,
                Constants.MOTOR_PWM_DUTY_CYCLE,
                Constants.MOTOR_PWM_FREQUENCY
        );

        controller.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                controller.stop();
                controller.clean();
            }
        }, TimeUnit.MINUTES.toMillis(1));
    }
}
