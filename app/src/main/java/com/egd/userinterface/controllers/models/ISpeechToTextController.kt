package com.egd.userinterface.controllers.models

import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnumAnnotation

/**
 * Abstraction for the controller responsible for managing the speech to text
 * functionality.
 *
 * @author Petar Krastev
 * @since 19.11.2017
 */
interface ISpeechToTextController : IController {

    /**
     * Converts the given speech input to a text output.
     *
     * @param type The type of content that will be recognized, must be one of [SpeechRecognitionTypesEnum]
     */
    fun recognizeSpeech(@SpeechRecognitionTypesEnumAnnotation type: String)
}
