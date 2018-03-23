package com.egd.userinterface.services.interfaces

import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface ITextToSpeechService {

    fun getInitState(): Observable<Unit>

    fun convertTextToSpeech(textToBeConverted: String): Observable<Unit>

    fun release()
}
