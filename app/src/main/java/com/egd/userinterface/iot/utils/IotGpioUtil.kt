package com.egd.userinterface.iot.utils

import com.egd.userinterface.iot.constants.annotations.IotGpioPortsRaspberryPiAnnotation
import com.egd.userinterface.iot.constants.annotations.IotGpioPwmRaspberryPiAnnotation
import com.egd.userinterface.iot.constants.annotations.IotInputGpioEdgeTriggerTypesAnnotation
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService
import com.google.android.things.pio.Pwm
import java.io.IOException

/**
 * Helper class used to initialize GPIOs, contains annotation types used
 * to prevent errors.
 *
 * @author Petar Krastev
 * @since 26.10.2017
 */
object IotGpioUtil {

    /**
     * Configures a GPIO as an input according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type [IotGpioPortsRaspberryPiEnum]
     * @param isHighActiveType If true, the active type of the GPIO will be set to [Gpio.ACTIVE_HIGH], false otherwise - [Gpio.ACTIVE_LOW]
     * @return Configured [Gpio]
     * @throws IOException If the configuration of the [Gpio] fails
     */
    @Throws(IOException::class)
    fun configureInputGpio(
            @IotGpioPortsRaspberryPiAnnotation pinName: String,
            isHighActiveType: Boolean,
            @IotInputGpioEdgeTriggerTypesAnnotation triggerType: Int,
            callback: GpioCallback
    ): Gpio {
        val service = PeripheralManagerService()
        val gpio = service.openGpio(pinName)
        gpio.setDirection(Gpio.DIRECTION_IN)
        gpio.setActiveType(if (isHighActiveType) Gpio.ACTIVE_HIGH else Gpio.ACTIVE_LOW)
        gpio.setEdgeTriggerType(triggerType)
        gpio.registerGpioCallback(callback)

        return gpio
    }

    /**
     * Configures a GPIO as an output according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type [IotGpioPortsRaspberryPiEnum]
     * @param isInitiallyHigh  If true, the GPIO will have an initial value of high, [Gpio.DIRECTION_OUT_INITIALLY_HIGH], false otherwise - [Gpio.DIRECTION_OUT_INITIALLY_LOW]
     * @param isHighActiveType If true, the active type of the GPIO will be set to [Gpio.ACTIVE_HIGH], false otherwise - [Gpio.ACTIVE_LOW]
     * @return Configured [Gpio]
     * @throws IOException If the configuration of the [Gpio] fails
     */
    @Throws(IOException::class)
    fun configureOutputGpio(
            @IotGpioPortsRaspberryPiAnnotation pinName: String,
            isInitiallyHigh: Boolean,
            isHighActiveType: Boolean
    ): Gpio {
        val service = PeripheralManagerService()
        val gpio = service.openGpio(pinName)
        gpio.setDirection(if (isInitiallyHigh) Gpio.DIRECTION_OUT_INITIALLY_HIGH else Gpio.DIRECTION_OUT_INITIALLY_LOW)
        gpio.setActiveType(if (isHighActiveType) Gpio.ACTIVE_HIGH else Gpio.ACTIVE_LOW)

        return gpio
    }

    /**
     * Configures a GPIO as a PWM output signal according to the specifications.
     *
     * @param pinName   Name of the GPIO port, must be one of type [IotGpioPortsRaspberryPiEnum]
     * @param dutyCycle Specifies the transition from high to low voltage, or vice versa, as a percentage for the PWM signal
     * @param frequency Specifies the frequency of the PWM signal
     * @return Configured [Gpio]
     * @throws IOException If the configuration of the [Gpio] fails
     */
    @Throws(IOException::class)
    fun configurePwm(
            @IotGpioPwmRaspberryPiAnnotation pinName: String,
            dutyCycle: Double,
            frequency: Double
    ): Pwm {
        val service = PeripheralManagerService()
        val pwm = service.openPwm(pinName)
        pwm.setPwmFrequencyHz(frequency)
        pwm.setPwmDutyCycle(dutyCycle)

        return pwm
    }
}
