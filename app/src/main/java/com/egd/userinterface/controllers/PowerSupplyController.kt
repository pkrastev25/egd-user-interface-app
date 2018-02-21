package com.egd.userinterface.controllers

import android.util.Log

import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnum
import com.egd.userinterface.constants.enums.GPIOPortsRaspberryPiEnumAnnotation
import com.egd.userinterface.controllers.models.IController
import com.egd.userinterface.utils.GPIOUtil
import com.google.android.things.pio.Gpio

import java.io.IOException

/**
 * A class used to manage a GPIO pin as a power supply. Internally
 * it uses [Gpio] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 24.11.2017
 */
class PowerSupplyController
/**
 * Initializes the [PowerSupplyController]. Configures a pin
 * as an output which will be used as a power source.
 *
 * @param pin The pin which will be configured as the power source
 */
(@GPIOPortsRaspberryPiEnumAnnotation pin: String) : IController {

    // INPUT/OUTPUT helpers
    private var mPowerSupply: Gpio? = null

    init {
        try {
            mPowerSupply = GPIOUtil.configureOutputGPIO(
                    pin,
                    true,
                    true
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configureOutputGPIO() failed!", e)
        }

    }

    /**
     * Releases all resources held by the [PowerSupplyController]
     * class.
     */
    override fun clean() {
        try {
            mPowerSupply!!.close()
        } catch (e: Exception) {
            Log.e(TAG, "PowerSupplyController.clean() failed!", e)
        }

        mPowerSupply = null
    }

    companion object {

        /**
         * Represents the class name, used only for debugging.
         */
        private val TAG = PowerSupplyController::class.java.simpleName
    }
}
