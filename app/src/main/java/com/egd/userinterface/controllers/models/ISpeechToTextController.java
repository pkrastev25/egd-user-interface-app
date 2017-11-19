package com.egd.userinterface.controllers.models;

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
     */
    void recognizeSpeech();
}
