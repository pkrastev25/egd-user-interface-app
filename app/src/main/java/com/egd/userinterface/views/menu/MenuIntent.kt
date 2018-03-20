package com.egd.userinterface.views.menu

/**
 * Created by User on 28.2.2018 г..
 */
sealed class MenuIntent {

    object TextToSpeechInitSuccessIntent : MenuIntent()

    object TextToSpeechInitErrorIntent : MenuIntent()

    data class TextToSpeechConvertErrorIntent(
            val error: Throwable
    ) : MenuIntent()

    object SpeechToTextInitSuccessIntent : MenuIntent()

    object SpeechToTextInitErrorIntent : MenuIntent()

    object SpeechToTextStopConvertIntent : MenuIntent()

    data class SpeechToTextConvertErrorIntent(
            val error: Throwable
    ) : MenuIntent()

    object PreviousMenuButtonIntent : MenuIntent()

    object NextMenuButtonIntent : MenuIntent()

    object BackMenuButtonIntent : MenuIntent()

    object ConfirmMenuButtonIntent : MenuIntent()
}