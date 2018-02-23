package com.egd.userinterface.view.states

sealed class MenuViewState {

    data class InitializationState(
            val isSpeechToTextInitialized: Boolean,
            val isTextToSpeechInitialized: Boolean
    ) : MenuViewState()

    data class CurrentMenuState(
            val currentMenuState: String
    ) : MenuViewState()

    data class ErrorState(
            val errorCause: String
    ) : MenuViewState()

    object InitializationErrorState : MenuViewState()
}