package com.egd.userinterface.views.menu

import android.content.Context
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.interactors.SpeechToTextInteractor
import com.egd.userinterface.interactors.TextToSpeechInteractor
import com.egd.userinterface.services.SpeechToTextService
import com.egd.userinterface.services.TextToSpeechService
import com.egd.userinterface.services.interfaces.ISpeechToTextService
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.views.menu.interfaces.IMenuActivity
import com.egd.userinterface.views.menu.interfaces.IMenuViewStateReducer
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class MenuPresenter(context: Context) : MviBasePresenter<IMenuActivity, MenuViewState>() {

    private val mTextToSpeechService: ITextToSpeechService = TextToSpeechService(
            context,
            Constants.TEXT_TO_SPEECH_DEFAULT_LANGUAGE,
            Constants.TEXT_TO_SPEECH_DEFAULT_PITCH,
            Constants.TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE
    )
    private val mSpeechToTextService: ISpeechToTextService = SpeechToTextService(context, mTextToSpeechService)

    private val mTextToSpeechInteractor = TextToSpeechInteractor(mTextToSpeechService)
    private val mSpeechToTextInteractor = SpeechToTextInteractor(mSpeechToTextService)

    private val mMenuViewStateReducer: IMenuViewStateReducer = MenuViewStateReducer(context)

    override fun bindIntents() {
        val textToSpeechInitIntent: Observable<MenuIntent> = mTextToSpeechInteractor.getTextToSpeechInitIntent()

        val textToSpeechConvertIntent: Observable<MenuIntent> = mTextToSpeechInteractor.getTextToSpeechConvertIntent()

        val speechToTextInitIntent: Observable<MenuIntent> = mSpeechToTextInteractor.getSpeechToTextInitIntent()

        val speechToTextConvertIntent: Observable<MenuIntent> = mSpeechToTextInteractor.getSpeechToTextConvertIntent()

        val previousMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::onPreviousMenuButtonIntent)
                .map {
                    MenuIntent.PreviousMenuButtonIntent
                }

        val nextMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::onNextMenuButtonIntent)
                .map {
                    MenuIntent.NextMenuButtonIntent
                }

        val backMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::onBackMenuButtonIntent)
                .map {
                    MenuIntent.BackMenuButtonIntent
                }

        val confirmMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::onConfirmMenuButtonIntent)
                .map {
                    MenuIntent.ConfirmMenuButtonIntent
                }

        val allIntents = Observable.mergeArray(
                textToSpeechInitIntent,
                textToSpeechConvertIntent,
                speechToTextInitIntent,
                speechToTextConvertIntent,
                previousMenuButtonIntent,
                nextMenuButtonIntent,
                backMenuButtonIntent,
                confirmMenuButtonIntent
        )

        val initialState = MenuViewState.InitState()
        val stateObservable = allIntents.scan(
                initialState,
                mMenuViewStateReducer::reduceState
        )

        subscribeViewState(stateObservable, IMenuActivity::render)
    }

    override fun destroy() {
        super.destroy()

        mTextToSpeechService.release()
        mSpeechToTextService.release()
    }
}