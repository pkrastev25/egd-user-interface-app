package com.egd.userinterface.services.interfaces

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalMotorService {

    fun getInitState(): Observable<Unit>

    fun startMotor(): Observable<Unit>

    fun stopMotor(): Observable<Unit>

    fun release()
}