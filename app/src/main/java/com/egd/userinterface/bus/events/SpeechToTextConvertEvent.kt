package com.egd.userinterface.bus.events

import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation

/**
 * Created by User on 9.3.2018 г..
 */
data class SpeechToTextConvertEvent(
        @SpeechRecognitionTypesAnnotation
        val speechRecognitionType: String
)