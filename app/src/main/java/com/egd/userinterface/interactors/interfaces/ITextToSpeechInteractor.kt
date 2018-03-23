package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface ITextToSpeechInteractor {

    fun getTextToSpeechInitStateIntent(): Observable<MenuIntent>

    fun getTextToSpeechConvertStateIntent(): Observable<MenuIntent>

    fun release()
}