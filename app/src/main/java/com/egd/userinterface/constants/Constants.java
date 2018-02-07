package com.egd.userinterface.constants;

import com.egd.userinterface.constants.enums.GPIOPWMRaspberryPiEnum;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum;

import java.util.Locale;

/**
 * Container for all the constants within this application.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
public class Constants {

    // --------------------------------------------------------------
    // region TEXT TO SPEECH
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
    // endregion TEXT TO SPEECH
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region LED
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO pin that will serve as input for the
     * {@link com.egd.userinterface.controllers.LEDController}.
     */
    public static final String LED_GPIO_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN29;

    /**
     * Specifies the GPIO pin that will serve as output for the
     * {@link com.egd.userinterface.controllers.LEDController}.
     */
    public static final String LED_GPIO_OUTPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN7;

    // --------------------------------------------------------------
    // endregion LED
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region MOTOR
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO pin that will serve as input for the
     * {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final String MOTOR_GPIO_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN35;

    /**
     * Specifies the GPIO pin that will serve as output for the
     * {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final String MOTOR_GPIO_OUTPUT = GPIOPWMRaspberryPiEnum.PWM_PIN12;

    /**
     * Specifies the transition from high to low voltage, or vice versa,
     * as a percentage of the {@link com.google.android.things.pio.Pwm}
     * for the {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final double MOTOR_PWM_DUTY_CYCLE = 50;

    /**
     * Specifies the frequency of the {@link com.google.android.things.pio.Pwm} for the
     * {@link com.egd.userinterface.controllers.MotorController}.
     */
    public static final double MOTOR_PWM_FREQUENCY = 0.5;

    // --------------------------------------------------------------
    // endregion MOTOR
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region SPEECH TO TEXT
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO pin that will serve as input for the
     * {@link com.egd.userinterface.controllers.SpeechToTextController}.
     */
    public static final String SPEECH_TO_TEXT_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN37;

    /**
     * Specifies the timeout for the speech recognition. After the specified time,
     * the speech recognition will stop.
     */
    public static final int SPEECH_TO_TEXT_TIMEOUT = 10_000;

    // --------------------------------------------------------------
    // endregion SPEECH TO TEXT
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region POWER SUPPLY
    // --------------------------------------------------------------

    /**
     * Specifies the GPIO that will be used as output and power supply for the
     * buttons, used by {@link com.egd.userinterface.controllers.PowerSupplyController}.
     */
    public static final String POWER_SUPPLY_BUTTONS = GPIOPortsRaspberryPiEnum.GPIO_PIN40;

    // --------------------------------------------------------------
    // endregion POWER SUPPLY
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region MENU
    // --------------------------------------------------------------

    /**
     * TODO: Dummy just for testing, replace when the menu is actually implemented
     */
    public static final String MENU_INPUT_BUTTON_5 = GPIOPortsRaspberryPiEnum.GPIO_PIN31;

    /**
     * TODO: Dummy just for testing, replace when the menu is actually implemented
     */
    public static final String MENU_INPUT_BUTTON_8 = GPIOPortsRaspberryPiEnum.GPIO_PIN23;

    // --------------------------------------------------------------
    // endregion MENU
    // --------------------------------------------------------------

    // --------------------------------------------------------------
    // region GPIO
    // --------------------------------------------------------------

    /**
     * Specifies the sample time between two or more interrupts. Used mainly
     * as a debouncing strategy for the {@link com.google.android.things.pio.Gpio}
     * configured as inputs.
     */
    public static final int GPIO_CALLBACK_SAMPLE_TIME_MS = 1000;

    // --------------------------------------------------------------
    // endregion GPIO
    // --------------------------------------------------------------
}
