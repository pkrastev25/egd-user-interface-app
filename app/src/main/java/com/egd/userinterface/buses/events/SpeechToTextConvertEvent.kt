package com.egd.userinterface.buses.events

import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation

/**
 * @author Petar Krastev
 */
data class SpeechToTextConvertEvent(
        @SpeechRecognitionTypesAnnotation
        val speechRecognitionType: String
)