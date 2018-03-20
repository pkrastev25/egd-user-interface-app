package com.egd.userinterface.iot.controllers

import android.content.Context
import android.os.Handler
import android.util.Log
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.iot.constants.annotations.IotGpioPortsRaspberryPiAnnotation
import com.egd.userinterface.iot.constants.enums.IotInputGpioEdgeTriggerTypesEnum
import com.egd.userinterface.iot.controllers.interfaces.ILEDController
import com.egd.userinterface.iot.utils.IotGpioUtil
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import java.io.IOException

/**
 * Helper class used to manage the state of the external LEDs. Internally
 * it uses [Gpio] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
class LEDController
/**
 * Initializes the [LEDController].
 * Includes a debouncing mechanism for the inputs which ignores all incoming
 * interrupts for [Constants.GPIO_CALLBACK_SAMPLE_TIME_MS] after successfully
 * detecting the 1st interrupt. Greatly improves performance!
 *
 * @param input  The [Gpio] that will be configured as input
 * @param output The [Gpio] that will be configured as output
 */
(private var mContext: Context?, @IotGpioPortsRaspberryPiAnnotation input: String, @IotGpioPortsRaspberryPiAnnotation output: String) : ILEDController {

    // INPUT/OUTPUT helpers
    private var mInput: Gpio? = null
    private var mOutput: Gpio? = null
    private var mInputCallback: GpioCallback? = null

    // STATE helpers
    private var mIsActive: Boolean = false
    private var mShouldDetectEdge: Boolean = false

    init {
        mShouldDetectEdge = true
        mInputCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                if (mShouldDetectEdge) {
                    mShouldDetectEdge = false

                    if (mIsActive) {
                        LEDsOFF()
                    } else {
                        LEDsON()
                    }

                    Handler().postDelayed({ mShouldDetectEdge = true }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS.toLong())
                }

                return true
            }

            override fun onGpioError(gpio: Gpio?, error: Int) {
                Log.e(TAG, "GpioCallback.onGpioError()")
                super.onGpioError(gpio, error)
            }
        }

        try {
            mInput = IotGpioUtil.configureInputGpio(
                    input,
                    true,
                    IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback as GpioCallback
            )
        } catch (e: IOException) {
            Log.e(TAG, "IotGpioUtil.configureInputGpio() failed!", e)
        }

        try {
            mOutput = IotGpioUtil.configureOutputGpio(
                    output,
                    false,
                    true
            )
        } catch (e: IOException) {
            Log.e(TAG, "IotGpioUtil.configureInputGpio() failed!", e)
        }

    }

    /**
     * Changes the state of all LEDs to ON by giving the required
     * voltage level to the LED pins.
     */
    override fun LEDsON() {
        mIsActive = true

        try {
            mOutput!!.value = true
            /*
            TextToSpeechService.instance.convertTextToSpeech(
                    mContext!!.getString(R.string.led_feedback_on)
            )
            */
        } catch (e: IOException) {
            Log.e(TAG, "LEDController.LEDsOn() failed!", e)
        }

    }

    /**
     * Changes the state of all LEDs to OFF by giving the required
     * voltage level to the LED pins.
     */
    override fun LEDsOFF() {
        mIsActive = false

        try {
            mOutput!!.value = false
            /*
            TextToSpeechService.instance.convertTextToSpeech(
                    mContext!!.getString(R.string.led_feedback_off)
            )
            */
        } catch (e: IOException) {
            Log.e(TAG, "LEDController.LEDsOff() failed!", e)
        }

    }

    /**
     * Releases all resources held by the [LEDController]
     * class.
     */
    override fun clean() {
        try {
            mInput!!.unregisterGpioCallback(mInputCallback)
            mInput!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "LEDController.clean() failed!", e)
        }

        try {
            mOutput!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "LEDController.clean() failed!", e)
        }

        mInput = null
        mInputCallback = null
        mOutput = null
        mContext = null
    }

    companion object {

        /**
         * Represents the class name, used only for debugging.
         */
        private val TAG = LEDController::class.java.simpleName
    }
}
