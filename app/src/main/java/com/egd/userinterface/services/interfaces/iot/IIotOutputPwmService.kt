package com.egd.userinterface.services.interfaces.iot

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IIotOutputPwmService {

    fun getInitState(): Observable<Unit>

    fun enabledPwmOutput(): Observable<Unit>

    fun disablePwmOutput(): Observable<Unit>

    fun release()
}