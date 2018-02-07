package com.egd.userinterface.controllers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.egd.userinterface.R;
import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerTypesEnum;
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum;
import com.egd.userinterface.controllers.models.ILEDController;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.IOException;

/**
 * Helper class used to manage the state of the external LEDs. Internally
 * it uses {@link Gpio} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
public class LEDController implements ILEDController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = LEDController.class.getSimpleName();

    private Context mContext;

    // INPUT/OUTPUT helpers
    private Gpio mInput;
    private Gpio mOutput;
    private GpioCallback mInputCallback;

    // STATE helpers
    private boolean mIsActive;
    private boolean mShouldDetectEdge;

    /**
     * Initializes the {@link LEDController}.
     * Includes a debouncing mechanism for the inputs which ignores all incoming
     * interrupts for {@link Constants#GPIO_CALLBACK_SAMPLE_TIME_MS} after successfully
     * detecting the 1st interrupt. Greatly improves performance!
     *
     * @param input  The {@link Gpio} that will be configured as input
     * @param output The {@link Gpio} that will be configured as output
     */
    public LEDController(Context context, @GPIOPortsRaspberryPiEnum String input, @GPIOPortsRaspberryPiEnum String output) {
        mContext = context;
        mShouldDetectEdge = true;
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
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
                    input,
                    true,
                    GPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }

        try {
            mOutput = GPIOUtil.configureOutputGPIO(
                    output,
                    false,
                    true
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e);
        }
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
            TextToSpeechController.getInstance().speak(
                    mContext.getString(R.string.led_feedback_on)
            );
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
            TextToSpeechController.getInstance().speak(
                    mContext.getString(R.string.led_feedback_off)
            );
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
        } catch (Exception e) {
            Log.e(TAG, "LEDController.clean() failed!", e);
        }

        try {
            mOutput.close();
        } catch (Exception e) {
            Log.e(TAG, "LEDController.clean() failed!", e);
        }

        mInput = null;
        mInputCallback = null;
        mOutput = null;
        mContext = null;
    }
}
