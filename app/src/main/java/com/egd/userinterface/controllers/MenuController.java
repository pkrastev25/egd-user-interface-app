package com.egd.userinterface.controllers;

import android.util.Log;

import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPi;
import com.egd.userinterface.controllers.models.IController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

/**
 * TODO: Dummy class used only for testing. Replace the contents with the actual implementation.
 *
 * @author Petar
 * @since 24.11.2017
 */
public class MenuController implements IController {

    private static final String TAG = MenuController.class.getSimpleName();

    // INPUT/OUTPUT helpers
    private Gpio mButton5;
    private Gpio mButton8;

    /**
     * TODO
     *
     * @param button5
     * @param button8
     */
    public MenuController(@GPIOPortsRaspberryPi String button5, @GPIOPortsRaspberryPi String button8) {
        try {
            mButton5 = GPIOUtil.configureInputGPIO(
                    button5,
                    true,
                    GPIOEdgeTriggerType.EDGE_RISING,
                    new GpioCallback() {
                        @Override
                        public boolean onGpioEdge(Gpio gpio) {
                            return super.onGpioEdge(gpio);
                        }
                    }
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        try {
            mButton8 = GPIOUtil.configureInputGPIO(
                    button8,
                    true,
                    GPIOEdgeTriggerType.EDGE_RISING,
                    new GpioCallback() {
                        @Override
                        public boolean onGpioEdge(Gpio gpio) {
                            return super.onGpioEdge(gpio);
                        }
                    }
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }
    }

    /**
     * TODO
     */
    @Override
    public void clean() {
        try {
            mButton5.close();
        } catch (IOException e) {
            Log.e(TAG, "MenuController.clean() failed!", e);
        }

        try {
            mButton8.close();
        } catch (IOException e) {
            Log.e(TAG, "MenuController.clean() failed!", e);
        }

        mButton5 = null;
        mButton8 = null;
    }
}
