package com.egd.userinterface.controllers;

import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.controllers.models.IMotorController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

/**
 * Singleton, used to manage the state of the external motor. Internally
 * used {@link Gpio} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
public class MotorController implements IMotorController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = MotorController.class.getSimpleName();
    private static IMotorController sInstance;

    // INPUT/OUTPUT helpers
    private Gpio mInput;
    private Pwm mPWMOutput;
    private GpioCallback mInputCallback;

    // STATE helpers
    private boolean mIsActive;

    /**
     * Initializes the {@link MotorController}. Configures a pin as input
     * according to {@link Constants#MOTOR_GPIO_INPUT} and a pin as output
     * according to {@link Constants#MOTOR_GPIO_OUTPUT}.
     */
    private MotorController() {
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                if (mIsActive) {
                    stop();
                } else {
                    start();
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
                    Constants.MOTOR_GPIO_INPUT,
                    true,
                    GPIOEdgeTriggerType.EDGE_RISING,
                    mInputCallback
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        try {
            mPWMOutput = GPIOUtil.configurePWM(
                    Constants.MOTOR_GPIO_OUTPUT,
                    Constants.MOTOR_PWM_DUTY_CYCLE,
                    Constants.MOTOR_PWM_FREQUENCY
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configurePWM()", e);
        }
    }

    /**
     * Initializes the {@link MotorController} instance.
     */
    public static void initialize() {
        if (sInstance == null) {
            synchronized (MotorController.class) {
                if (sInstance == null) {
                    sInstance = new MotorController();
                }
            }
        }
    }

    /**
     * Expose the only instance of {@link MotorController}.
     *
     * @return The {@link MotorController} instance
     * @throws RuntimeException If {@link MotorController#initialize()} is not called before this method
     */
    public static IMotorController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call MotorController.initialize() first!");
        }

        return sInstance;
    }

    /**
     * Starts the motor by enabling the PWM ouput.
     */
    @Override
    public void start() {
        synchronized (MotorController.class) {
            mIsActive = true;

            try {
                mPWMOutput.setEnabled(true);
            } catch (IOException e) {
                Log.e(TAG, "MotorController.start() failed!", e);
            }
        }
    }

    /**
     * Stops the motor by disabling the PWM output.
     */
    @Override
    public void stop() {
        synchronized (MotorController.class) {
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
        sInstance = null;
        mIsActive = false;
    }
}
