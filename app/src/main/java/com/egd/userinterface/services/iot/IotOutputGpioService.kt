package com.egd.userinterface.services.iot

import android.content.Context
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.constants.annotations.iot.IotGpioPortsRaspberryPiAnnotation
import com.egd.userinterface.services.interfaces.iot.IIotOutputGpioService
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.io.IOException

/**
 * @author Petar Krastev
 */
class IotOutputGpioService(
        context: Context,
        @IotGpioPortsRaspberryPiAnnotation
        pinName: String,
        isInitiallyHigh: Boolean,
        isHighActiveType: Boolean
) : IIotOutputGpioService {

    private val mContext = context.applicationContext
    private var mOutputGpio: Gpio?
    private val mInitState = BehaviorSubject.create<Unit>()

    init {
        try {
            val service = PeripheralManagerService()
            mOutputGpio = service.openGpio(pinName)
            mOutputGpio?.let {
                it.setDirection(
                        if (isInitiallyHigh) {
                            Gpio.DIRECTION_OUT_INITIALLY_HIGH
                        } else {
                            Gpio.DIRECTION_OUT_INITIALLY_LOW
                        }
                )
                it.setActiveType(
                        if (isHighActiveType) {
                            Gpio.ACTIVE_HIGH
                        } else {
                            Gpio.ACTIVE_LOW
                        }
                )
            }
            mInitState.onComplete()
        } catch (e: IOException) {
            Log.e(TAG, "IotOutputGpioService init failed!", e)
            mOutputGpio = null
            mInitState.mergeWith(getObservableError())
        }
    }

    override fun getInitState(): Observable<Unit> {
        return mInitState
    }

    override fun enableGpioOutput(): Observable<Unit> {
        return try {
            mOutputGpio?.let {
                it.value = true
            } ?: return getObservableError()

            Observable.empty<Unit>()
        } catch (e: IOException) {
            Log.e(TAG, "enableGpioOutput failed!", e)
            getObservableError()
        }
    }

    override fun disableGpioOutput(): Observable<Unit> {
        return try {
            mOutputGpio?.let {
                it.value = false
            } ?: return getObservableError()

            Observable.empty<Unit>()
        } catch (e: IOException) {
            Log.e(TAG, "disableGpioOutput failed!", e)
            getObservableError()
        }
    }

    override fun release() {
        mOutputGpio?.close()
    }

    private fun getObservableError(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.gpio_error_io)
                )
        )
    }

    companion object {
        private val TAG = IotOutputGpioService::class.java.simpleName
    }
}