package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalLedInteractor {

    fun getExternalLedInitStateIntent(): Observable<MenuIntent>

    fun getExternalLedToggleStateIntent(): Observable<MenuIntent>

    fun release()
}