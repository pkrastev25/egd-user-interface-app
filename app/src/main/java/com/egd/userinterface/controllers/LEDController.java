package com.egd.userinterface.controllers;

import android.os.Handler;
import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.controllers.models.ILEDController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

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

    // INPUT/OUTPUT helpers
    private Gpio mInput;
    private Gpio mOutput;
    private GpioCallback mInputCallback;

    // STATE helpers
    private boolean mIsActive;
    private boolean mShouldDetectEdge;

    /**
     * Initializes the {@link LEDController}. Configures a pin as input
     * according to {@link Constants#LED_GPIO_INPUT} and a pin as output
     * according to {@link Constants#LED_GPIO_OUTPUT}.
     */
    private LEDController() {
        mShouldDetectEdge = true;
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                // TODO: Verify if this is really needed
                if (mShouldDetectEdge) {
                    mShouldDetectEdge = false;

                    if (mIsActive) {
                        LEDsOFF();
                    } else {
                        LEDsON();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mShouldDetectEdge = true;
                        }
                    }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS);
                }

                return true;
            }

            @Override
            public void onGpioError(Gpio gpio, int error) {
                Log.e(TAG, "GpioCallback.onGpioError()");
                super.onGpioError(gpio, error);
            }
        };

        try {
            mInput = GPIOUtil.configureInputGPIO(
                    Constants.LED_GPIO_INPUT,
                    true,
                    GPIOEdgeTriggerType.EDGE_RISING,
                    mInputCallback
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        try {
            mOutput = GPIOUtil.configureOutputGPIO(
                    Constants.LED_GPIO_OUTPUT,
                    false,
                    true
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
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

        try {
            mOutput.setValue(true);
        } catch (IOException e) {
            Log.e(TAG, "LEDController.LEDsOn() failed!", e);
        }
    }

    /**
     * Changes the state of all LEDs to OFF by giving the required
     * voltage level to the LED pins.
     */
    @Override
    public void LEDsOFF() {
        mIsActive = false;

        try {
            mOutput.setValue(false);
        } catch (IOException e) {
            Log.e(TAG, "LEDController.LEDsOff() failed!", e);
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

        try {
            mOutput.close();
        } catch (IOException e) {
            Log.e(TAG, "LEDController.clean() failed!", e);
        }

        mInput = null;
        mInputCallback = null;
        mOutput = null;
        sInstance = null;
    }
}
