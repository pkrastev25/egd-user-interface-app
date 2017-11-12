package com.egd.userinterface.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPi;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManagerService;

/**
 * Helper class used to initialize GPIOs, contains annotation types used
 * to prevent errors.
 *
 * @author Petar Krastev
 * @since 26.10.2017
 */
public class GPIOUtil {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = GPIOUtil.class.getSimpleName();

    /**
     * Configures a GPIO as an input according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type {@link GPIOPortsRaspberryPi}
     * @param isHighActiveType If true, the active type of the GPIO will be set to {@link Gpio#ACTIVE_HIGH}, false otherwise, {@link Gpio#ACTIVE_LOW}
     * @return Configured {@link Gpio}, null if the configuration failed
     */
    @Nullable
    public static Gpio configureInputGPIO(@GPIOPortsRaspberryPi String pinName, boolean isHighActiveType, @GPIOEdgeTriggerType int triggerType, GpioCallback callback) {
        Gpio gpio = null;

        try {
            PeripheralManagerService service = new PeripheralManagerService();
            gpio = service.openGpio(pinName);
            gpio.setDirection(Gpio.DIRECTION_IN);
            gpio.setActiveType(isHighActiveType ? Gpio.ACTIVE_HIGH : Gpio.ACTIVE_LOW);
            gpio.setEdgeTriggerType(triggerType);
            gpio.registerGpioCallback(callback);
        } catch (Exception e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        return gpio;
    }

    /**
     * Configures a GPIO as an output according to the specifications.
     *
     * @param pinName          Name of the GPIO port, must be one of type {@link GPIOPortsRaspberryPi}
     * @param isInitiallyHigh  If true, the GPIO will have an initial value of high, {@link Gpio#DIRECTION_OUT_INITIALLY_HIGH}, false otherwise, {@link Gpio#DIRECTION_OUT_INITIALLY_LOW}
     * @param isHighActiveType If true, the active type of the GPIO will be set to {@link Gpio#ACTIVE_HIGH}, false otherwise, {@link Gpio#ACTIVE_LOW}
     * @return Configured {@link Gpio}, null if the configuration failed
     */
    @Nullable
    public static Gpio configureOutputGPIO(@GPIOPortsRaspberryPi String pinName, boolean isInitiallyHigh, boolean isHighActiveType) {
        Gpio gpio = null;

        try {
            PeripheralManagerService service = new PeripheralManagerService();
            gpio = service.openGpio(pinName);
            gpio.setDirection(isInitiallyHigh ? Gpio.DIRECTION_OUT_INITIALLY_HIGH : Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(isHighActiveType ? Gpio.ACTIVE_HIGH : Gpio.ACTIVE_LOW);
        } catch (Exception e) {
            Log.e(TAG, "GPIOUtil.configureOutputGPIO() failed!", e);
        }

        return gpio;
    }
}
