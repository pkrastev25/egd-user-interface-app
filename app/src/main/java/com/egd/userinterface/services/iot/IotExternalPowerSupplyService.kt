package com.egd.userinterface.services.iot

import com.egd.userinterface.services.interfaces.IExternalPowerSupplyService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class IotExternalPowerSupplyService(externalPowerSupply: IotOutputGpioService) : IExternalPowerSupplyService {

    private val mExternalPowerSupply = externalPowerSupply

    override fun getInitState(): Observable<Unit> {
        return mExternalPowerSupply.getInitState()
    }

    override fun release() {
        mExternalPowerSupply.release()
    }
}