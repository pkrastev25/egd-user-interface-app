package com.egd.userinterface.iot.controllers

import android.util.Log
import com.egd.userinterface.iot.constants.annotations.IotGpioPortsRaspberryPiAnnotation
import com.egd.userinterface.iot.controllers.interfaces.IController
import com.egd.userinterface.iot.utils.IotGpioUtil
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
(@IotGpioPortsRaspberryPiAnnotation pin: String) : IController {

    // INPUT/OUTPUT helpers
    private var mPowerSupply: Gpio? = null

    init {
        try {
            mPowerSupply = IotGpioUtil.configureOutputGpio(
                    pin,
                    true,
                    true
            )
        } catch (e: IOException) {
            Log.e(TAG, "IotGpioUtil.configureOutputGpio() failed!", e)
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
