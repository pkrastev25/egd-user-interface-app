package com.egd.userinterface.utils;

import android.util.Log;

import com.egd.userinterface.constants.enums.GPIOActiveTypes;
import com.egd.userinterface.constants.enums.RaspberryPiGPIOPorts;
import com.google.android.things.pio.Gpio;
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
     * Configures a GPIO according to the specifications.
     *
     * @param pinName Name of the GPIO port, must be one of type {@link RaspberryPiGPIOPorts}
     * @param activeType Sets Low or High voltage to the port, must be one of type {@link GPIOActiveTypes}
     * @return Configured {@link Gpio}
     */
    public static Gpio configureInputGPIO(@RaspberryPiGPIOPorts String pinName, @GPIOActiveTypes int activeType) {
        Gpio gpio = null;

        try {
            PeripheralManagerService service = new PeripheralManagerService();
            gpio = service.openGpio(pinName);
            gpio.setDirection(Gpio.DIRECTION_IN);
            gpio.setActiveType(activeType);
        } catch (Exception e) {
            Log.d(TAG, "GPIO config failed");
            e.printStackTrace();
        }

        return gpio;
    }

    /**
     * Configures a GPIO according to the specifications.
     *
     * @param pinName Name of the GPIO port, must be one of type {@link RaspberryPiGPIOPorts}
     * @param isInitiallyHigh If true, it will set {@link Gpio#DIRECTION_OUT_INITIALLY_HIGH}, false otherwise, {@link Gpio#DIRECTION_OUT_INITIALLY_LOW}
     * @param activeType Sets Low or High voltage to the port, must be one of type {@link GPIOActiveTypes}
     * @return Configured {@link Gpio}
     */
    public static Gpio configureOutputGPIO(@RaspberryPiGPIOPorts String pinName, boolean isInitiallyHigh, @GPIOActiveTypes int activeType) {
        Gpio gpio = null;

        try {
            PeripheralManagerService service = new PeripheralManagerService();
            gpio = service.openGpio(pinName);
            gpio.setDirection(isInitiallyHigh ? Gpio.DIRECTION_OUT_INITIALLY_HIGH : Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(activeType);
        } catch (Exception e) {
            Log.d(TAG, "GPIO config failed");
            e.printStackTrace();
        }

        return gpio;
    }
}
