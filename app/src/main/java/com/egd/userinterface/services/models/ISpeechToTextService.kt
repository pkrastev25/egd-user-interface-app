package com.egd.userinterface.services.models

import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnumAnnotation

/**
 * Abstraction for the controller responsible for managing the speech to text
 * functionality.
 *
 * @author Petar Krastev
 * @since 19.11.2017
 */
interface ISpeechToTextService {

    fun recognizeSpeech(@SpeechRecognitionTypesEnumAnnotation speechRecognitionType: String)

    fun release()
}
