package com.egd.userinterface.services.interfaces

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalPowerSupplyService {

    fun getInitState(): Observable<Unit>

    fun release()
}