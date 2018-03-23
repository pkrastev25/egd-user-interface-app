package com.egd.userinterface.services

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.services.interfaces.IExternalLedService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class ExternalLedService(
        context: Context
) : IExternalLedService {

    private val mContext = context.applicationContext

    override fun getInitState(): Observable<Unit> {
        // Fake the initialization, otherwise the app for smart phone devices in unusable
        return Observable.empty()
    }

    override fun turnLedOn(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.external_led_error_missing)
                )
        )
    }

    override fun turnLedOff(): Observable<Unit> {
        return Observable.error(
                Throwable(
                        mContext.getString(R.string.external_led_error_missing)
                )
        )
    }

    override fun release() {
        // Do nothing, for now...
    }
}