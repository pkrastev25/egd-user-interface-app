package com.egd.userinterface.controllers;

import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.controllers.models.IMotorController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

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

    private Gpio mInput;
    private Gpio mOutput;
    private GpioCallback mInputCallback;
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

        mInput = GPIOUtil.configureInputGPIO(
                Constants.MOTOR_GPIO_INPUT,
                true,
                GPIOEdgeTriggerType.EDGE_RISING,
                mInputCallback
        );

        mOutput = GPIOUtil.configureOutputGPIO(
                Constants.MOTOR_GPIO_OUTPUT,
                false,
                true
        );
    }

    /**
     * Initializes the {@link MotorController} instance.
     */
    public static void initialize() {
        if (sInstance == null) {
            sInstance = new MotorController();
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
     * Start the motor by giving the required voltage level to the
     * motor output pin.
     */
    @Override
    public void start() {
        if (!mIsActive) {
            mIsActive = true;
            runMotor();
        }
    }

    /**
     * Stop the motor by giving the required voltage level to the
     * motor output pin.
     */
    @Override
    public void stop() {
        mIsActive = false;
    }

    /**
     * Releases all resources held by the {@link MotorController}
     * class.
     */
    @Override
    public void clean() {
        if (mIsActive) {
            mIsActive = false;
        }

        try {
            mInput.unregisterGpioCallback(mInputCallback);
            mInput.close();
            mOutput.close();
        } catch (IOException e) {
            Log.e(TAG, "MotorController.clean() failed!", e);
        }

        mInputCallback = null;
        mInput = null;
        mOutput = null;
        sInstance = null;
    }

    /**
     * Toggles the ON and OFF state of the motor every
     * {@link Constants#MOTOR_ON_OFF_SWITCH_TIME}.
     */
    private void runMotor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean highVoltage = true;

                while (mIsActive) {
                    try {
                        mOutput.setValue(highVoltage);
                        highVoltage = !highVoltage;
                        Thread.sleep(Constants.MOTOR_ON_OFF_SWITCH_TIME);
                    } catch (Exception e) {
                        Log.e(TAG, "MotorController.runMotor() failed!", e);
                    }
                }
            }
        }).start();
    }
}
