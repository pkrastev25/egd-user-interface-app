package com.egd.userinterface.controllers;

import android.util.Log;

import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum;
import com.egd.userinterface.controllers.models.IController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

/**
 * A class used to manage a GPIO pin as a power supply. Internally
 * it uses {@link Gpio} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 24.11.2017
 */
public class PowerSupplyController implements IController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = PowerSupplyController.class.getSimpleName();

    // INPUT/OUTPUT helpers
    private Gpio mPowerSupply;

    /**
     * Initializes the {@link PowerSupplyController}. Configures a pin
     * as an output which will be used as a power source.
     *
     * @param pin The pin which will be configured as the power source
     */
    public PowerSupplyController(@GPIOPortsRaspberryPiEnum String pin) {
        try {
            mPowerSupply = GPIOUtil.configureOutputGPIO(
                    pin,
                    true,
                    true
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureOutputGPIO() failed!", e);
        }
    }

    /**
     * Releases all resources held by the {@link PowerSupplyController}
     * class.
     */
    @Override
    public void clean() {
        try {
            mPowerSupply.close();
        } catch (Exception e) {
            Log.e(TAG, "PowerSupplyController.clean() failed!", e);
        }

        mPowerSupply = null;
    }
}
