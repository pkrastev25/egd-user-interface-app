package com.egd.userinterface.controllers

import android.os.Handler
import android.util.Log

import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.enums.InputGPIOEdgeTriggerTypesEnum
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnumAnnotation
import com.egd.userinterface.controllers.models.IController
import com.egd.userinterface.utils.GPIOUtil
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback

import java.io.IOException

/**
 * TODO: Dummy class used only for testing. Replace the contents with the actual implementation.
 *
 * @author Petar
 * @since 24.11.2017
 */
class MenuController
/**
 * TODO
 * Include a debouncing mechanism for the inputs which ignores all incoming
 * interrupts for [Constants.GPIO_CALLBACK_SAMPLE_TIME_MS] after successfully
 * detecting the 1st interrupt. Greatly improves performance!
 *
 * @param button5
 * @param button8
 */
(@GPIOPortsRaspberryPiEnumAnnotation button5: String, @GPIOPortsRaspberryPiEnumAnnotation button8: String) : IController {

    // INPUT/OUTPUT helpers
    private var mButton5: Gpio? = null
    private var mButton8: Gpio? = null

    private var mShouldDetectEdgeButton5: Boolean = false
    private var mShouldDetectEdgeButton8: Boolean = false

    init {
        try {
            mButton5 = GPIOUtil.configureInputGPIO(
                    button5,
                    true,
                    InputGPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    object : GpioCallback() {
                        override fun onGpioEdge(gpio: Gpio?): Boolean {
                            if (mShouldDetectEdgeButton5) {
                                mShouldDetectEdgeButton5 = false

                                Handler().postDelayed({ mShouldDetectEdgeButton5 = true }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS.toLong())
                            }

                            return true
                        }
                    }
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e)
        }

        try {
            mButton8 = GPIOUtil.configureInputGPIO(
                    button8,
                    true,
                    InputGPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    object : GpioCallback() {
                        override fun onGpioEdge(gpio: Gpio?): Boolean {
                            if (mShouldDetectEdgeButton8) {
                                mShouldDetectEdgeButton8 = false

                                Handler().postDelayed({ mShouldDetectEdgeButton8 = true }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS.toLong())
                            }

                            return true
                        }
                    }
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO() failed!", e)
        }

    }

    /**
     * TODO
     */
    override fun clean() {
        try {
            mButton5!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "MenuController.clean() failed!", e)
        }

        try {
            mButton8!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "MenuController.clean() failed!", e)
        }

        mButton5 = null
        mButton8 = null
    }

    companion object {

        private val TAG = MenuController::class.java.simpleName
    }
}
