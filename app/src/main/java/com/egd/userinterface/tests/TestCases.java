package com.egd.userinterface.tests;

import com.egd.userinterface.controllers.TextToSpeechController;

/**
 * Contains test cases for all modules in this application.
 *
 * @author Petar Krastev
 * @since 29.10.2017
 */
public class TestCases {

    /**
     * Simple test case for the {@link TextToSpeechController}.
     */
    public static void TextToSpeechControllerTest() {
        TextToSpeechController.getInstance().speak("Hello, this is the text to speech feature from Android");
        TextToSpeechController.getInstance().speak("If you are hearing this, it successfully worked");
    }
}
