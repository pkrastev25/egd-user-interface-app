package com.egd.userinterface.controllers;

import android.os.Handler;
import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerTypesEnum;
import com.egd.userinterface.constants.enums.GPIOPWMRaspberryPiEnum;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum;
import com.egd.userinterface.controllers.models.IMotorController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

/**
 * Helper class used to manage the state of the external motor. Internally
 * it uses {@link Gpio}/{@link Pwm} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
public class MotorController implements IMotorController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = MotorController.class.getSimpleName();

    // INPUT/OUTPUT helpers
    private Gpio mInput;
    private Pwm mPWMOutput;
    private GpioCallback mInputCallback;

    // STATE helpers
    private boolean mIsActive;
    private boolean mShouldDetectEdge;

    /**
     * Initializes the {@link MotorController}.
     * Includes a debouncing mechanism for the inputs which ignores all incoming
     * interrupts for {@link Constants#GPIO_CALLBACK_SAMPLE_TIME_MS} after successfully
     * detecting the 1st interrupt. Greatly improves performance!
     *
     * @param input     The {@link Gpio} that will be configured as input
     * @param output    The {@link Pwm} that will be configured as output
     * @param dutyCycle Specifies the duty cycle of the {@link Pwm} output
     * @param frequency Specifies the frequency of the {@link Pwm} output
     */
    public MotorController(
            @GPIOPortsRaspberryPiEnum String input,
            @GPIOPWMRaspberryPiEnum String output,
            double dutyCycle,
            double frequency
    ) {
        mShouldDetectEdge = true;
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                if (mShouldDetectEdge) {
                    mShouldDetectEdge = false;

                    if (mIsActive) {
                        stop();
                    } else {
                        start();
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
                Log.e(TAG, "GpioCallback.onGpioError() called!");
                super.onGpioError(gpio, error);
            }
        };

        try {
            mInput = GPIOUtil.configureInputGPIO(
                    input,
                    true,
                    GPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        try {
            mPWMOutput = GPIOUtil.configurePWM(
                    output,
                    dutyCycle,
                    frequency
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configurePWM()", e);
        }
    }

    /**
     * Starts the motor by enabling the {@link Pwm} ouput.
     */
    @Override
    public void start() {
        if (!mIsActive) {
            mIsActive = true;

            try {
                mPWMOutput.setEnabled(true);
            } catch (IOException e) {
                Log.e(TAG, "MotorController.start() failed!", e);
            }
        }
    }

    /**
     * Stops the motor by disabling the {@link Pwm} output.
     */
    @Override
    public void stop() {
        if (mIsActive) {
            mIsActive = false;

            try {
                mPWMOutput.setEnabled(false);
            } catch (IOException e) {
                Log.e(TAG, "MotorController.stop() failed!", e);
            }
        }
    }

    /**
     * Releases all resources held by the {@link MotorController}
     * class.
     */
    @Override
    public void clean() {
        try {
            mInput.unregisterGpioCallback(mInputCallback);
            mInput.close();
        } catch (Exception e) {
            Log.e(TAG, "MotorController.clean() failed!", e);
        }

        try {
            mPWMOutput.close();
        } catch (Exception e) {
            Log.e(TAG, "MotorController.clean() failed!", e);
        }

        mInputCallback = null;
        mInput = null;
        mPWMOutput = null;
    }
}
