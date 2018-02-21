package com.egd.userinterface.constants

import com.egd.userinterface.constants.enums.GPIOPWMRaspberryPiEnum
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum

import java.util.Locale

/**
 * Container for all the constants within this application.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
object Constants {

    /**
     * Specifies the default language for [com.egd.userinterface.controllers.TextToSpeechController].
     */
    val TEXT_TO_SPEECH_DEFAULT_LANGUAGE = Locale.US!!

    /**
     * Specifies the default pitch for [com.egd.userinterface.controllers.TextToSpeechController].
     */
    const val TEXT_TO_SPEECH_DEFAULT_PITCH = 1f

    /**
     * Specifies the default speech rate for [com.egd.userinterface.controllers.TextToSpeechController].
     */
    const val TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE = 1f

    /**
     * Specifies the GPIO pin that will serve as input for the
     * [com.egd.userinterface.controllers.LEDController].
     */
    const val LED_GPIO_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN29

    /**
     * Specifies the GPIO pin that will serve as output for the
     * [com.egd.userinterface.controllers.LEDController].
     */
    const val LED_GPIO_OUTPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN7

    /**
     * Specifies the GPIO pin that will serve as input for the
     * [com.egd.userinterface.controllers.MotorController].
     */
    const val MOTOR_GPIO_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN35

    /**
     * Specifies the GPIO pin that will serve as output for the
     * [com.egd.userinterface.controllers.MotorController].
     */
    const val MOTOR_GPIO_OUTPUT = GPIOPWMRaspberryPiEnum.PWM_PIN12

    /**
     * Specifies the transition from high to low voltage, or vice versa,
     * as a percentage of the [com.google.android.things.pio.Pwm]
     * for the [com.egd.userinterface.controllers.MotorController].
     */
    const val MOTOR_PWM_DUTY_CYCLE = 50.0

    /**
     * Specifies the frequency of the [com.google.android.things.pio.Pwm] for the
     * [com.egd.userinterface.controllers.MotorController].
     */
    const val MOTOR_PWM_FREQUENCY = 0.5

    /**
     * Specifies the GPIO pin that will serve as input for the
     * [com.egd.userinterface.controllers.SpeechToTextController].
     */
    const val SPEECH_TO_TEXT_INPUT = GPIOPortsRaspberryPiEnum.GPIO_PIN37

    /**
     * Specifies the timeout for the speech recognition. After the specified time,
     * the speech recognition will stop.
     */
    const val SPEECH_TO_TEXT_TIMEOUT = 10000

    /**
     * Specifies the GPIO that will be used as output and power supply for the
     * buttons, used by [com.egd.userinterface.controllers.PowerSupplyController].
     */
    const val POWER_SUPPLY_BUTTONS = GPIOPortsRaspberryPiEnum.GPIO_PIN40

    /**
     * TODO: Dummy just for testing, replace when the menu is actually implemented
     */
    const val MENU_INPUT_BUTTON_5 = GPIOPortsRaspberryPiEnum.GPIO_PIN31

    /**
     * TODO: Dummy just for testing, replace when the menu is actually implemented
     */
    const val MENU_INPUT_BUTTON_8 = GPIOPortsRaspberryPiEnum.GPIO_PIN23

    /**
     * Specifies the sample time between two or more interrupts. Used mainly
     * as a debouncing strategy for the [com.google.android.things.pio.Gpio]
     * configured as inputs.
     */
    const val GPIO_CALLBACK_SAMPLE_TIME_MS = 1000
}
