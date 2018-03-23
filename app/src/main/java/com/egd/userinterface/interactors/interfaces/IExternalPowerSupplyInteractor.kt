package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IExternalPowerSupplyInteractor {

    fun getExternalPowerSupplyInitState(): Observable<MenuIntent>

    fun release()
}