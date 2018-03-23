package com.egd.userinterface.services.interfaces

import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface ISpeechToTextService {

    fun getInitState(): Observable<Unit>

    fun recognizeSpeech(@SpeechRecognitionTypesAnnotation speechRecognitionType: String): Observable<Unit>

    fun release()
}
