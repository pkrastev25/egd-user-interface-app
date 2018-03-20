package com.egd.userinterface.interactors.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable

/**
 * Created by User on 11.3.2018 г..
 */
interface ITextToSpeechInteractor {

    fun getTextToSpeechInitIntent(): Observable<MenuIntent>

    fun getTextToSpeechConvertIntent(): Observable<MenuIntent>

    fun release()
}