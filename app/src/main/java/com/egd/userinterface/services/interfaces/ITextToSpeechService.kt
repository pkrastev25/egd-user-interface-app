package com.egd.userinterface.services.interfaces

import io.reactivex.Observable

/**
 * Abstraction for the controller responsible for managing the text to speech
 * functionality.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
interface ITextToSpeechService {

    fun getInitState(): Observable<Unit>

    fun convertTextToSpeech(textToBeConverted: String): Observable<Unit>

    fun release()
}
