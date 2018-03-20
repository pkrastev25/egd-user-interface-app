package com.egd.userinterface.services.interfaces

import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation
import io.reactivex.Observable

/**
 * Abstraction for the controller responsible for managing the speech to text
 * functionality.
 *
 * @author Petar Krastev
 * @since 19.11.2017
 */
interface ISpeechToTextService {

    fun getInitState(): Observable<Unit>

    fun recognizeSpeech(@SpeechRecognitionTypesAnnotation speechRecognitionType: String): Observable<Unit>

    fun release()
}
