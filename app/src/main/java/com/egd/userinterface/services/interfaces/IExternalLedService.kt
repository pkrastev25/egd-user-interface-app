package com.egd.userinterface.services.interfaces

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalLedService {

    fun getInitState(): Observable<Unit>

    fun turnLedOn(): Observable<Unit>

    fun turnLedOff(): Observable<Unit>

    fun release()
}