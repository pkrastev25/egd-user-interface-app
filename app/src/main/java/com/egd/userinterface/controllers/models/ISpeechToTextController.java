package com.egd.userinterface.controllers.models;

import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum;

/**
 * Abstraction for the controller responsible for managing the speech to text
 * functionality.
 *
 * @author Petar Krastev
 * @since 19.11.2017
 */
public interface ISpeechToTextController extends IController {

    /**
     * Converts the given speech input to a text output.
     *
     * @param type The type of content that will be recognized, must be one of {@link SpeechRecognitionTypesEnum}
     */
    void recognizeSpeech(@SpeechRecognitionTypesEnum String type);
}
