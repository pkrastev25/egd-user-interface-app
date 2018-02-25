package com.egd.userinterface.controllers

import android.content.Context
import android.os.Handler
import android.util.Log

import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.enums.*
import com.egd.userinterface.controllers.models.IMotorController
import com.egd.userinterface.services.TextToSpeechService
import com.egd.userinterface.utils.GPIOUtil
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.Pwm

import java.io.IOException

/**
 * Helper class used to manage the state of the external motor. Internally
 * it uses [Gpio]/[Pwm] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 10.11.2017
 */
class MotorController
/**
 * Initializes the [MotorController].
 * Includes a debouncing mechanism for the inputs which ignores all incoming
 * interrupts for [Constants.GPIO_CALLBACK_SAMPLE_TIME_MS] after successfully
 * detecting the 1st interrupt. Greatly improves performance!
 *
 * @param input     The [Gpio] that will be configured as input
 * @param output    The [Pwm] that will be configured as output
 * @param dutyCycle Specifies the duty cycle of the [Pwm] output
 * @param frequency Specifies the frequency of the [Pwm] output
 */
(
        private var mContext: Context?,
        @GPIOPortsRaspberryPiEnumAnnotation input: String,
        @GPIOPWMRaspberryPiEnumAnnotation output: String,
        dutyCycle: Double,
        frequency: Double
) : IMotorController {

    // INPUT/OUTPUT helpers
    private var mInput: Gpio? = null
    private var mPWMOutput: Pwm? = null
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
                        stop()
                    } else {
                        start()
                    }

                    Handler().postDelayed({ mShouldDetectEdge = true }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS.toLong())
                }

                return true
            }

            override fun onGpioError(gpio: Gpio?, error: Int) {
                Log.e(TAG, "GpioCallback.onGpioError() called!")
                super.onGpioError(gpio, error)
            }
        }

        try {
            mInput = GPIOUtil.configureInputGPIO(
                    input,
                    true,
                    InputGPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback as GpioCallback
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e)
        }

        try {
            mPWMOutput = GPIOUtil.configurePWM(
                    output,
                    dutyCycle,
                    frequency
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configurePWM()", e)
        }

    }

    /**
     * Starts the motor by enabling the [Pwm] ouput.
     */
    override fun start() {
        if (!mIsActive) {
            mIsActive = true

            try {
                mPWMOutput!!.setEnabled(true)
                /*
                TextToSpeechService.instance.convertTextToSpeech(
                        mContext!!.getString(R.string.motor_feedback_on)
                )
                */
            } catch (e: IOException) {
                Log.e(TAG, "MotorController.start() failed!", e)
            }

        }
    }

    /**
     * Stops the motor by disabling the [Pwm] output.
     */
    override fun stop() {
        if (mIsActive) {
            mIsActive = false

            try {
                mPWMOutput!!.setEnabled(false)
                /*
                TextToSpeechService.instance.convertTextToSpeech(
                        mContext!!.getString(R.string.motor_feedback_off)
                )
                */
            } catch (e: IOException) {
                Log.e(TAG, "MotorController.stop() failed!", e)
            }

        }
    }

    /**
     * Releases all resources held by the [MotorController]
     * class.
     */
    override fun clean() {
        try {
            mInput!!.unregisterGpioCallback(mInputCallback)
            mInput!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "MotorController.clean() failed!", e)
        }

        try {
            mPWMOutput!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "MotorController.clean() failed!", e)
        }

        mInputCallback = null
        mInput = null
        mPWMOutput = null
        mContext = null
    }

    companion object {

        /**
         * Represents the class name, used only for debugging.
         */
        private val TAG = MotorController::class.java.simpleName
    }
}
