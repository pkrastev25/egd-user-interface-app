package com.egd.userinterface.services.interfaces.iot

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IIotOutputGpioService {

    fun getInitState(): Observable<Unit>

    fun enableGpioOutput(): Observable<Unit>

    fun disableGpioOutput(): Observable<Unit>

    fun release()
}