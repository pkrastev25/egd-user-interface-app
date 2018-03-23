package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalMotorInteractor {

    fun getExternalMotorInitStateIntent(): Observable<MenuIntent>

    fun getExternalMotorToggleStateIntent(): Observable<MenuIntent>

    fun release()
}