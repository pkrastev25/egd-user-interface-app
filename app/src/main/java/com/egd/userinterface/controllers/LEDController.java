package com.egd.userinterface.controllers;

import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.controllers.models.ILEDController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton, used to manage the state of the external LEDs. Internally
 * used {@link Gpio} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
public class LEDController implements ILEDController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = LEDController.class.getSimpleName();
    private static ILEDController sInstance;

    private Gpio mInput;
    private List<Gpio> mOutputs;
    private GpioCallback mInputCallback;
    private boolean mIsActive;

    /**
     * Initializes the {@link LEDController}. Configures a pin as input
     * according to {@link Constants#LED_GPIO_INPUT} and a pin as output
     * according to {@link Constants#LED_GPIO_OUTPUT}.
     */
    private LEDController() {
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                if (mIsActive) {
                    LEDsOFF();
                } else {
                    LEDsON();
                }

                // Continue listening for more interrupts
                return true;
            }

            @Override
            public void onGpioError(Gpio gpio, int error) {
                Log.e(TAG, "GpioCallback.onGpioError()");
                super.onGpioError(gpio, error);
            }
        };

        mInput = GPIOUtil.configureInputGPIO(
                Constants.LED_GPIO_INPUT,
                true,
                GPIOEdgeTriggerType.EDGE_RISING,
                mInputCallback
        );
        mOutputs = new ArrayList<>(Constants.LED_GPIO_OUTPUT.length);

        for (String pinName : Constants.LED_GPIO_OUTPUT) {
            mOutputs.add(
                    GPIOUtil.configureOutputGPIO(
                            pinName,
                            false,
                            true
                    )
            );
        }
    }

    /**
     * Initializes the {@link LEDController} instance.
     */
    public static void initialize() {
        if (sInstance == null) {
            sInstance = new LEDController();
        }
    }

    /**
     * Expose the only instance of {@link LEDController}.
     *
     * @return The {@link LEDController} instance
     * @throws RuntimeException If {@link LEDController#initialize()} is not called before this method
     */
    public static ILEDController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call LEDController.initialize() first!");
        }

        return sInstance;
    }

    /**
     * Changes the state of all LEDs to ON by giving the required
     * voltage level to the LED pins.
     */
    @Override
    public void LEDsON() {
        mIsActive = true;

        for (Gpio gpio : mOutputs) {
            try {
                gpio.setValue(true);
            } catch (IOException e) {
                Log.e(TAG, "LEDController.LEDsOn() failed!", e);
            }
        }
    }

    /**
     * Changes the state of all LEDs to OFF by giving the required
     * voltage level to the LED pins.
     */
    @Override
    public void LEDsOFF() {
        mIsActive = false;

        for (Gpio gpio : mOutputs) {
            try {
                gpio.setValue(false);
            } catch (IOException e) {
                Log.e(TAG, "LEDController.LEDsOff() failed!", e);
            }
        }
    }

    /**
     * Releases all resources held by the {@link LEDController}
     * class.
     */
    @Override
    public void clean() {
        try {
            mInput.unregisterGpioCallback(mInputCallback);
            mInput.close();
        } catch (IOException e) {
            Log.e(TAG, "LEDController.clean() failed!", e);
        }

        if (mOutputs != null) {
            for (Gpio gpio : mOutputs) {
                try {
                    gpio.close();
                } catch (IOException e) {
                    Log.e(TAG, "LEDController.clean() failed!", e);
                }
            }

            mInput = null;
            mInputCallback = null;
            mOutputs.clear();
            mOutputs = null;
            sInstance = null;
        }
    }
}
