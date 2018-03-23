package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface ISpeechToTextInteractor {

    fun getSpeechToTextInitStateIntent(): Observable<MenuIntent>

    fun getSpeechToTextConvertStateIntent(): Observable<MenuIntent>

    fun release()
}