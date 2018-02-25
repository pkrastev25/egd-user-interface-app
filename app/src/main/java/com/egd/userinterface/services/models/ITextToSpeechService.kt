package com.egd.userinterface.services.models

/**
 * Abstraction for the controller responsible for managing the text to speech
 * functionality.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
interface ITextToSpeechService {

    fun convertTextToSpeech(textToBeConverted: String)

    fun release()
}
