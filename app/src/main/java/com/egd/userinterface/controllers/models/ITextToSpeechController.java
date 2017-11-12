package com.egd.userinterface.controllers.models;

/**
 * Abstraction for the controller responsible for managing the text to speech
 * functionality.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
public interface ITextToSpeechController extends IController {

    /**
     * Converts the given text input to a speech output.
     *
     * @param output Text to be converted
     */
    void speak(String output);
}
