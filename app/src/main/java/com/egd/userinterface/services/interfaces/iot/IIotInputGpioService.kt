package com.egd.userinterface.services.interfaces.iot

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IIotInputGpioService {

    fun getInitState(): Observable<Unit>

    fun getInputIntent(): Observable<Unit>

    fun release()
}