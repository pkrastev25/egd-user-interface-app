package com.egd.userinterface.views.menu

/**
 * @author Petar Krastev
 */
sealed class MenuViewState {

    data class InitState(
            val isSpeechToTextInit: Boolean = false,
            val isTextToSpeechInit: Boolean = false
    ) : MenuViewState()

    object InitErrorState : MenuViewState()

    data class OptionSpeechRecognitionStartState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    object SpeechRecognitionState : MenuViewState()

    data class OptionExternalLedState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    data class OptionExternalLedTurnLedOnState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    data class OptionExternalLedTurnLedOffState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    data class OptionExternalMotorState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    data class OptionExternalMotorStartMotorState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()

    data class OptionExternalMotorStopMotorState(
            val stateMessage: String,
            val error: Throwable? = null
    ) : MenuViewState()
}