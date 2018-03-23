package com.egd.userinterface.views.menu

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.buses.ReducerInteractorCommunicationBus
import com.egd.userinterface.buses.events.ExternalLedToggleEvent
import com.egd.userinterface.buses.events.ExternalMotorToggleEvent
import com.egd.userinterface.buses.events.SpeechToTextConvertEvent
import com.egd.userinterface.buses.events.TextToSpeechConvertEvent
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import com.egd.userinterface.views.menu.interfaces.IMenuViewStateReducer

/**
 * @author Petar Krastev
 */
class MenuViewStateReducer(context: Context) : IMenuViewStateReducer {

    private val mContext = context.applicationContext

    override fun reduceState(
            previousState: MenuViewState,
            changes: MenuIntent
    ): MenuViewState {
        return when (changes) {
            is MenuIntent.TextToSpeechInitSuccessIntent -> {
                reduceTextToSpeechInitSuccessIntent(
                        previousState
                )
            }
            is MenuIntent.TextToSpeechInitErrorIntent -> {
                reduceTextToSpeechInitErrorIntent()
            }
            is MenuIntent.TextToSpeechConvertErrorIntent -> {
                reduceTextToSpeechConvertErrorIntent(
                        previousState, changes
                )
            }
            is MenuIntent.SpeechToTextInitSuccessIntent -> {
                reduceSpeechToTextInitIntent(
                        previousState
                )
            }
            is MenuIntent.SpeechToTextInitErrorIntent -> {
                reduceSpeechToTextInitErrorIntent()
            }
            is MenuIntent.SpeechToTextStopConvertIntent -> {
                reduceSpeechToTextStopConvertIntent(
                        previousState
                )
            }
            is MenuIntent.SpeechToTextConvertErrorIntent -> {
                reduceSpeechToTextConvertErrorIntent(
                        previousState, changes
                )
            }
            is MenuIntent.PreviousMenuButtonIntent -> {
                reducePreviousMenuButtonIntent(
                        previousState
                )
            }
            is MenuIntent.NextMenuButtonIntent -> {
                reduceNextMenuButtonIntent(
                        previousState
                )
            }
            is MenuIntent.BackMenuButtonIntent -> {
                reduceBackMenuButtonIntent(
                        previousState
                )
            }
            is MenuIntent.ConfirmMenuButtonIntent -> {
                reduceConfirmMenuButtonIntent(
                        previousState
                )
            }
            is MenuIntent.ExternalDeviceInitErrorIntent -> {
                reduceExternalDeviceInitErrorIntent()
            }
            is MenuIntent.ExternalDeviceErrorIntent -> {
                reduceExternalDeviceErrorIntent(
                        previousState, changes
                )
            }
        }
    }

    private fun reduceTextToSpeechInitSuccessIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.InitState -> {
                if (previousState.isSpeechToTextInit) {
                    val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                    publishTextToSpeechConvertEvent(stateMessage)
                    MenuViewState.OptionSpeechRecognitionStartState(
                            stateMessage = stateMessage
                    )
                } else {
                    MenuViewState.InitState(
                            isTextToSpeechInit = true
                    )
                }
            }
            else -> previousState
        }
    }

    private fun reduceTextToSpeechInitErrorIntent(): MenuViewState {
        return MenuViewState.InitErrorState
    }

    private fun reduceTextToSpeechConvertErrorIntent(
            previousState: MenuViewState,
            changes: MenuIntent.TextToSpeechConvertErrorIntent
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionSpeechRecognitionStartState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalLedState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalMotorState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            else -> previousState
        }
    }

    private fun reduceSpeechToTextInitIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.InitState -> {
                if (previousState.isTextToSpeechInit) {
                    val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                    publishTextToSpeechConvertEvent(stateMessage)
                    MenuViewState.OptionSpeechRecognitionStartState(
                            stateMessage = stateMessage
                    )
                } else {
                    MenuViewState.InitState(
                            isSpeechToTextInit = true
                    )
                }
            }
            else -> previousState
        }
    }

    private fun reduceSpeechToTextInitErrorIntent(): MenuViewState {
        return MenuViewState.InitErrorState
    }

    private fun reduceSpeechToTextStopConvertIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.SpeechRecognitionState -> {
                val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionSpeechRecognitionStartState(
                        stateMessage = stateMessage
                )
            }
            else -> previousState
        }
    }

    private fun reduceSpeechToTextConvertErrorIntent(
            previousState: MenuViewState,
            changes: MenuIntent.SpeechToTextConvertErrorIntent
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.SpeechRecognitionState -> {
                val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionSpeechRecognitionStartState(
                        stateMessage = stateMessage,
                        error = changes.error
                )
            }
            else -> previousState
        }
    }

    private fun reducePreviousMenuButtonIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionSpeechRecognitionStartState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedState -> {
                val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionSpeechRecognitionStartState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options_turn_led_off)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedTurnLedOffState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options_turn_led_on)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedTurnLedOnState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options_stop_motor)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorStopMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options_start_motor)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorStartMotorState(
                        stateMessage = stateMessage
                )
            }
            else -> previousState
        }
    }

    private fun reduceNextMenuButtonIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionSpeechRecognitionStartState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_speech_recognition_start)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionSpeechRecognitionStartState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options_turn_led_off)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedTurnLedOffState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options_turn_led_on)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedTurnLedOnState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options_stop_motor)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorStopMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options_start_motor)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorStartMotorState(
                        stateMessage = stateMessage
                )
            }
            else -> previousState
        }
    }

    private fun reduceBackMenuButtonIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorState(
                        stateMessage = stateMessage
                )
            }
            else -> previousState
        }
    }

    private fun reduceConfirmMenuButtonIntent(
            previousState: MenuViewState
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionExternalLedState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_led_options_turn_led_on)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalLedTurnLedOnState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                ReducerInteractorCommunicationBus.publish(
                        ExternalLedToggleEvent(
                                shouldTurnOn = true
                        )
                )
                previousState
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                ReducerInteractorCommunicationBus.publish(
                        ExternalLedToggleEvent(
                                shouldTurnOn = true
                        )
                )
                previousState
            }
            is MenuViewState.OptionExternalMotorState -> {
                val stateMessage = mContext.getString(R.string.menu_state_external_motor_options_start_motor)

                publishTextToSpeechConvertEvent(stateMessage)
                MenuViewState.OptionExternalMotorStartMotorState(
                        stateMessage = stateMessage
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                ReducerInteractorCommunicationBus.publish(
                        ExternalMotorToggleEvent(
                                shouldStart = true
                        )
                )
                previousState
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                ReducerInteractorCommunicationBus.publish(
                        ExternalMotorToggleEvent(
                                shouldStart = false
                        )
                )
                previousState
            }
            is MenuViewState.OptionSpeechRecognitionStartState -> {
                ReducerInteractorCommunicationBus.publish(
                        SpeechToTextConvertEvent(
                                SpeechRecognitionTypesEnum.ALL_KEYWORDS
                        )
                )
                MenuViewState.SpeechRecognitionState
            }
            else -> previousState
        }
    }

    private fun reduceExternalDeviceInitErrorIntent(): MenuViewState {
        return MenuViewState.InitErrorState
    }

    private fun reduceExternalDeviceErrorIntent(
            previousState: MenuViewState,
            changes: MenuIntent.ExternalDeviceErrorIntent
    ): MenuViewState {
        return when (previousState) {
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                previousState.copy(
                        error = changes.error
                )
            }
            else -> previousState
        }
    }

    private fun publishTextToSpeechConvertEvent(stateMessage: String) {
        ReducerInteractorCommunicationBus.publish(
                TextToSpeechConvertEvent(stateMessage)
        )
    }
}