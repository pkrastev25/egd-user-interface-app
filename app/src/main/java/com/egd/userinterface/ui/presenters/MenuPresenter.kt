package com.egd.userinterface.ui.presenters

import android.content.Context
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.event.bus.EventBus
import com.egd.userinterface.event.bus.events.SpeechToTextInitializationEvent
import com.egd.userinterface.event.bus.events.TextToSpeechInitializationEvent
import com.egd.userinterface.services.SpeechToTextService
import com.egd.userinterface.services.TextToSpeechService
import com.egd.userinterface.services.models.ISpeechToTextService
import com.egd.userinterface.services.models.ITextToSpeechService
import com.egd.userinterface.ui.views.IMainActivity
import com.egd.userinterface.view.states.MenuViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class MenuPresenter(context: Context) : MviBasePresenter<IMainActivity, MenuViewState>() {

    private var mContext: Context? = context
    private lateinit var mTextToSpeechService: ITextToSpeechService
    private lateinit var mSpeechToTextService: ISpeechToTextService

    override fun bindIntents() {
        val textToSpeechInitializationEventIntent: Observable<MenuViewState> = EventBus.listen(TextToSpeechInitializationEvent::class.java)
                .map {
                    if (it.isSuccessful) {
                        MenuViewState.InitializationState(false, it.isSuccessful)
                    } else {
                        MenuViewState.InitializationErrorState
                    }
                }

        val speechToTextInitializationEventIntent: Observable<MenuViewState> = EventBus.listen(SpeechToTextInitializationEvent::class.java)
                .map {
                    if (it.isSuccessful) {
                        MenuViewState.InitializationState(it.isSuccessful, false)
                    } else {
                        MenuViewState.InitializationErrorState
                    }
                }

        val previousMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onPreviousMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Previous button clicked!")
                }

        val nextMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onNextMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Next button clicked!")
                }

        val backMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onBackMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Back button clicked!")
                }

        val confirmMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onConfirmMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Confirm button clicked!")
                }

        val allIntents = Observable.mergeArray(
                speechToTextInitializationEventIntent,
                textToSpeechInitializationEventIntent,
                previousMenuButtonIntent,
                nextMenuButtonIntent,
                backMenuButtonIntent,
                confirmMenuButtonIntent
        )
        val initialState = MenuViewState.InitializationState(false, false)
        val stateObservable = allIntents.scan(initialState, this::viewStateReducer)

        subscribeViewState(stateObservable, IMainActivity::render)
        initializeServices()
    }

    override fun destroy() {
        super.destroy()

        releaseResources()
    }

    private fun viewStateReducer(previousState: MenuViewState, changes: MenuViewState): MenuViewState {
        when (changes) {
            is MenuViewState.InitializationState -> {
                return when (previousState) {
                    is MenuViewState.InitializationState -> {
                        val isTextToSpeechInitialized = previousState.isTextToSpeechInitialized || changes.isTextToSpeechInitialized
                        val isSpeechToTextInitialized = previousState.isSpeechToTextInitialized || changes.isSpeechToTextInitialized

                        if (isTextToSpeechInitialized && isSpeechToTextInitialized) {
                            MenuViewState.CurrentMenuState("Start!")
                        } else {
                            changes
                        }
                    }
                    else -> changes
                }
            }
            else -> return changes
        }
    }

    private fun initializeServices() {
        mTextToSpeechService = TextToSpeechService(
                mContext!!,
                Constants.TEXT_TO_SPEECH_DEFAULT_LANGUAGE,
                Constants.TEXT_TO_SPEECH_DEFAULT_PITCH,
                Constants.TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE
        )
        mSpeechToTextService = SpeechToTextService(mContext!!)
    }

    private fun releaseResources() {
        mTextToSpeechService.release()
        mSpeechToTextService.release()
        mContext = null
    }
}