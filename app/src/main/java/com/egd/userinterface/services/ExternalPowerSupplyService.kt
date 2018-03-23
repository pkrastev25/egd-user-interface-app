package com.egd.userinterface.services

import com.egd.userinterface.services.interfaces.IExternalPowerSupplyService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class ExternalPowerSupplyService : IExternalPowerSupplyService {

    override fun getInitState(): Observable<Unit> {
        // Fake the initialization, otherwise the app for smart phone devices in unusable
        return Observable.empty()
    }

    override fun release() {
        // Do nothing, for now...
    }
}