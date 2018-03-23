package com.egd.userinterface.services.iot

import android.content.Context
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.constants.annotations.iot.IotGpioPortsRaspberryPiAnnotation
import com.egd.userinterface.constants.annotations.iot.IotInputGpioEdgeTriggerTypesAnnotation
import com.egd.userinterface.services.interfaces.iot.IIotInputGpioService
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.io.IOException

/**
 * @author Petar Krastev
 */
class IotInputGpioService(
        context: Context,
        @IotGpioPortsRaspberryPiAnnotation
        pinName: String,
        isHighActiveType: Boolean,
        @IotInputGpioEdgeTriggerTypesAnnotation
        triggerType: Int
) : IIotInputGpioService {

    private val mContext = context.applicationContext
    private var mInputGpio: Gpio?
    private val mInputGpioCallback: GpioCallback
    private val mInitState = BehaviorSubject.create<Unit>()
    private val mInputIntent = PublishSubject.create<Unit>()

    init {
        mInputGpioCallback = object : GpioCallback() {
            override fun onGpioError(gpio: Gpio?, error: Int) {
                super.onGpioError(gpio, error)
                Log.e(TAG, "onGpioError called with error code: $error")
                mInputIntent.mergeWith(getObservableError())
            }

            override fun onGpioEdge(gpio: Gpio?): Boolean {
                mInputIntent.onNext(Unit)
                return super.onGpioEdge(gpio)
            }
        }

        try {
            val service = PeripheralManagerService()
            mInputGpio = service.openGpio(pinName)
            mInputGpio?.let {
                it.setDirection(Gpio.DIRECTION_IN)
                it.setActiveType(
                        if (isHighActiveType) {
                            Gpio.ACTIVE_HIGH
                        } else {
                            Gpio.ACTIVE_LOW
                        }
                )
                it.setEdgeTriggerType(triggerType)
                it.registerGpioCallback(mInputGpioCallback)
            }
            mInputIntent.onComplete()
        } catch (e: IOException) {
            Log.e(TAG, "IotInputGpioService init failed!", e)
            mInputGpio = null
            mInputIntent.mergeWith(getObservableError())
        }
    }

    override fun getInitState(): Observable<Unit> {
        return mInitState
    }

    override fun getInputIntent(): Observable<Unit> {
        return mInputIntent
    }

    override fun release() {
        mInputIntent.onComplete()
        mInputGpio?.let {
            it.unregisterGpioCallback(mInputGpioCallback)
            it.close()
        }
    }

    private fun getObservableError(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.gpio_error_io)
                )
        )
    }

    companion object {
        private val TAG = IotInputGpioService::class.java.simpleName
    }
}