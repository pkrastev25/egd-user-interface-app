package com.egd.userinterface.iot.utils

import android.util.Log
import com.google.android.things.pio.PeripheralManagerService

/**
 * Created by User on 25.2.2018 г..
 */
object IotHardwareUtil {

    private var TAG = IotHardwareUtil::class.java.simpleName

    fun areGpioPinsAvailableOnDevice(): Boolean {
        return try {
            val service = PeripheralManagerService()
            service.gpioList.isNotEmpty()
        } catch (e: ClassNotFoundException) {
            Log.d(TAG, "areGpioPinsAvailableOnDevice() failed!", e)
            false
        }
    }

    fun arePwmPinsAvailableOnDevice(): Boolean {
        return try {
            val service = PeripheralManagerService()
            service.pwmList.isNotEmpty()
        } catch (e: Exception) {
            Log.d(TAG, "arePwmPinsAvailableOnDevice() failed!", e)
            false
        }
    }
}