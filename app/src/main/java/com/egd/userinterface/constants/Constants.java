package com.egd.userinterface.constants;

import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPi;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Container for all the constants within this application.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
public class Constants {

    // --------------------------------------------------------------
    // TEXT TO SPEECH CONTROLLER region
    // --------------------------------------------------------------

    /**
     * Specifies the default language for {@link com.egd.userinterface.controllers.TextToSpeechController}.
     */
    public static final Locale TEXT_TO_SPEECH_DEFAULT_LANGUAGE = Locale.US;

    /**
     * Specifies the default pitch for {@link com.egd.userinterface.controllers.TextToSpeechController}.
     */
    public static final float TEXT_TO_SPEECH_DEFAULT_PITCH = 1f;

    /**
     * Specifies the default speech rate for {@link com.egd.userinterface.controllers.TextToSpeechController}.
     */
    public static final float TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE = 1f;

    // --------------------------------------------------------------
    // End of TEXT TO SPEECH CONTROLLER region
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // LED region
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO pin that will serve as input for the
     * {@link com.egd.userinterface.controllers.LEDController}.
     */
    public static final String LED_GPIO_INPUT = GPIOPortsRaspberryPi.GPIO_PIN29;

    /**
     * Specifies the GPIO pins that will serve as output for the
     * {@link com.egd.userinterface.controllers.LEDController}.
     */
    public static final String[] LED_GPIO_OUTPUT = {
            GPIOPortsRaspberryPi.GPIO_PIN7,
            GPIOPortsRaspberryPi.GPIO_PIN11,
            GPIOPortsRaspberryPi.GPIO_PIN13,
            GPIOPortsRaspberryPi.GPIO_PIN15
    };

    // --------------------------------------------------------------
    // End of LED region
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // MOTOR region
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO pin that will serve as input for the
     * {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final String MOTOR_GPIO_INPUT = GPIOPortsRaspberryPi.GPIO_PIN31;

    /**
     * Specifies the GPIO pin that will serve as output for the
     * {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final String MOTOR_GPIO_OUTPUT = GPIOPortsRaspberryPi.GPIO_PIN32;

    /**
     * Specifies the time after which the motor should switch its
     * state from ON to OFF and vice versa.
     */
    public static final long MOTOR_ON_OFF_SWITCH_TIME = TimeUnit.SECONDS.toMillis(2);

    // --------------------------------------------------------------
    // End of MOTOR region
    // --------------------------------------------------------------
}
