package com.egd.userinterface.utils;

import com.egd.userinterface.constants.enums.GPIOEdgeTriggerTypesEnum;
import com.egd.userinterface.constants.enums.GPIOPWMRaspberryPiEnum;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

/**
 * Helper class used to initialize GPIOs, contains annotation types used
 * to prevent errors.
 *
 * @author Petar Krastev
 * @since 26.10.2017
 */
public class GPIOUtil {

    /**
     * Configures a GPIO as an input according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type {@link GPIOPortsRaspberryPiEnum}
     * @param isHighActiveType If true, the active type of the GPIO will be set to {@link Gpio#ACTIVE_HIGH}, false otherwise, {@link Gpio#ACTIVE_LOW}
     * @return Configured {@link Gpio}
     * @throws IOException If the configuration of the {@link Gpio} fails
     */
    public static Gpio configureInputGPIO(@GPIOPortsRaspberryPiEnum String pinName, boolean isHighActiveType, @GPIOEdgeTriggerTypesEnum int triggerType, GpioCallback callback) throws IOException {
        PeripheralManagerService service = new PeripheralManagerService();
        Gpio gpio = service.openGpio(pinName);
        gpio.setDirection(Gpio.DIRECTION_IN);
        gpio.setActiveType(isHighActiveType ? Gpio.ACTIVE_HIGH : Gpio.ACTIVE_LOW);
        gpio.setEdgeTriggerType(triggerType);
        gpio.registerGpioCallback(callback);

        return gpio;
    }

    /**
     * Configures a GPIO as an output according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type {@link GPIOPortsRaspberryPiEnum}
     * @param isInitiallyHigh  If true, the GPIO will have an initial value of high, {@link Gpio#DIRECTION_OUT_INITIALLY_HIGH}, false otherwise, {@link Gpio#DIRECTION_OUT_INITIALLY_LOW}
     * @param isHighActiveType If true, the active type of the GPIO will be set to {@link Gpio#ACTIVE_HIGH}, false otherwise, {@link Gpio#ACTIVE_LOW}
     * @return Configured {@link Gpio}
     * @throws IOException If the configuration of the {@link Gpio} fails
     */
    public static Gpio configureOutputGPIO(@GPIOPortsRaspberryPiEnum String pinName, boolean isInitiallyHigh, boolean isHighActiveType) throws IOException {
        PeripheralManagerService service = new PeripheralManagerService();
        Gpio gpio = service.openGpio(pinName);
        gpio.setDirection(isInitiallyHigh ? Gpio.DIRECTION_OUT_INITIALLY_HIGH : Gpio.DIRECTION_OUT_INITIALLY_LOW);
        gpio.setActiveType(isHighActiveType ? Gpio.ACTIVE_HIGH : Gpio.ACTIVE_LOW);

        return gpio;
    }

    /**
     * Configures a GPIO as a PWM output signal according to the specifications.
     *
     * @param pinName   Name of the GPIO port, must be one of type {@link GPIOPortsRaspberryPiEnum}
     * @param dutyCycle Specifies the transition from high to low voltage, or vice versa, as a percentage for the PWM signal
     * @param frequency Specifies the frequency of the PWM signal
     * @return Configured {@link Gpio}
     * @throws IOException If the configuration of the {@link Gpio} fails
     */
    public static Pwm configurePWM(@GPIOPWMRaspberryPiEnum String pinName, double dutyCycle, double frequency) throws IOException {
        PeripheralManagerService service = new PeripheralManagerService();
        Pwm pwm = service.openPwm(pinName);
        pwm.setPwmFrequencyHz(frequency);
        pwm.setPwmDutyCycle(dutyCycle);

        return pwm;
    }
}
