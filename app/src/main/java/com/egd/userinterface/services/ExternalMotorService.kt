package com.egd.userinterface.services

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.services.interfaces.IExternalMotorService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class ExternalMotorService(
        context: Context
) : IExternalMotorService {

    private val mContext = context.applicationContext

    override fun getInitState(): Observable<Unit> {
        // Fake the initialization, otherwise the app for smart phone devices in unusable
        return Observable.empty()
    }

    override fun startMotor(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.external_motor_error_missing)
                )
        )
    }

    override fun stopMotor(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.external_motor_error_missing)
                )
        )
    }

    override fun release() {
        // Do nothing, for now...
    }
}