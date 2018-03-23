package com.egd.userinterface.services.iot

import android.content.Context
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.constants.annotations.iot.IotGpioPwmRaspberryPiAnnotation
import com.egd.userinterface.services.interfaces.iot.IIotOutputPwmService
import com.google.android.things.pio.PeripheralManagerService
import com.google.android.things.pio.Pwm
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.io.IOException

/**
 * @author Petar Krastev
 */
class IotOutputPwmService(
        context: Context,
        @IotGpioPwmRaspberryPiAnnotation
        pinName: String,
        dutyCycle: Double,
        frequency: Double
) : IIotOutputPwmService {

    private val mContext = context.applicationContext
    private var mOutputPwm: Pwm?
    private val mInitState = BehaviorSubject.create<Unit>()

    init {
        try {
            val service = PeripheralManagerService()
            mOutputPwm = service.openPwm(pinName)
            mOutputPwm?.let {
                it.setPwmFrequencyHz(frequency)
                it.setPwmDutyCycle(dutyCycle)
            }
            mInitState.onComplete()
        } catch (e: IOException) {
            Log.e(TAG, "IotOutputGpioService init failed!", e)
            mOutputPwm = null
            mInitState.mergeWith(getObservableError())
        }
    }

    override fun getInitState(): Observable<Unit> {
        return mInitState
    }

    override fun enabledPwmOutput(): Observable<Unit> {
        return try {
            mOutputPwm?.setEnabled(true) ?: return getObservableError()

            Observable.empty<Unit>()
        } catch (e: IOException) {
            Log.e(TAG, "enabledPwmOutput failed!", e)
            getObservableError()
        }
    }

    override fun disablePwmOutput(): Observable<Unit> {
        return try {
            mOutputPwm?.setEnabled(false) ?: return getObservableError()

            Observable.empty<Unit>()
        } catch (e: IOException) {
            Log.e(TAG, "disablePwmOutput failed!", e)
            getObservableError()
        }
    }

    override fun release() {
        mOutputPwm?.close()
    }

    private fun getObservableError(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.gpio_error_io)
                )
        )
    }

    companion object {
        private val TAG = IotOutputPwmService::class.java.simpleName
    }
}