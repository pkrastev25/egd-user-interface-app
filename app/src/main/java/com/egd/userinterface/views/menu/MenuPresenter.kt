package com.egd.userinterface.views.menu

import android.content.Context
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.interactors.SpeechToTextInteractor
import com.egd.userinterface.interactors.TextToSpeechInteractor
import com.egd.userinterface.interactors.interfaces.*
import com.egd.userinterface.services.SpeechToTextService
import com.egd.userinterface.services.interfaces.ISpeechToTextService
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.views.menu.interfaces.IMenuActivity
import com.egd.userinterface.views.menu.interfaces.IMenuViewStateReducer
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @author Petar Krastev
 */
class MenuPresenter(
        context: Context,
        textToSpeechService: ITextToSpeechService,
        externalLedInteractor: IExternalLedInteractor,
        externalMotorInteractor: IExternalMotorInteractor,
        externalPowerSupplyInteractor: IExternalPowerSupplyInteractor
) : MviBasePresenter<IMenuActivity, MenuViewState>() {

    private val mTextToSpeechInteractor: ITextToSpeechInteractor
    private val mSpeechToTextInteractor: ISpeechToTextInteractor
    private val mExternalLedInteractor: IExternalLedInteractor = externalLedInteractor
    private val mExternalMotorInteractor: IExternalMotorInteractor = externalMotorInteractor
    private val mExternalPowerSupplyInteractor: IExternalPowerSupplyInteractor = externalPowerSupplyInteractor

    private val mMenuViewStateReducer: IMenuViewStateReducer = MenuViewStateReducer(context)

    // TODO: Create all with DI
    init {
        val speechToTextService: ISpeechToTextService = SpeechToTextService(
                context, textToSpeechService
        )

        mTextToSpeechInteractor = TextToSpeechInteractor(textToSpeechService)
        mSpeechToTextInteractor = SpeechToTextInteractor(speechToTextService)
    }

    override fun bindIntents() {
        val menuButtonInitStateIntent: Observable<MenuIntent> = intent(IMenuActivity::menuButtonInitStateIntent)
                .switchMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceInitErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
        val previousMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::previousMenuButtonIntent)
                .debounce(Constants.MENU_BUTTON_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .map<MenuIntent> {
                    MenuIntent.PreviousMenuButtonIntent
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
        val nextMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::nextMenuButtonIntent)
                .debounce(Constants.MENU_BUTTON_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .map<MenuIntent> {
                    MenuIntent.NextMenuButtonIntent
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
        val backMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::backMenuButtonIntent)
                .debounce(Constants.MENU_BUTTON_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .map<MenuIntent> {
                    MenuIntent.BackMenuButtonIntent
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
        val confirmMenuButtonIntent: Observable<MenuIntent> = intent(IMenuActivity::confirmMenuButtonIntent)
                .debounce(Constants.MENU_BUTTON_DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .map<MenuIntent> {
                    MenuIntent.ConfirmMenuButtonIntent
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())

        val textToSpeechInitStateIntent: Observable<MenuIntent> = mTextToSpeechInteractor.getTextToSpeechInitStateIntent()
        val textToSpeechConvertStateIntent: Observable<MenuIntent> = mTextToSpeechInteractor.getTextToSpeechConvertStateIntent()

        val speechToTextInitStateIntent: Observable<MenuIntent> = mSpeechToTextInteractor.getSpeechToTextInitStateIntent()
        val speechToTextConvertStateIntent: Observable<MenuIntent> = mSpeechToTextInteractor.getSpeechToTextConvertStateIntent()

        val externalLedInitStateIntent: Observable<MenuIntent> = mExternalLedInteractor.getExternalLedInitStateIntent()
        val externalLedToggleStateIntent: Observable<MenuIntent> = mExternalLedInteractor.getExternalLedToggleStateIntent()

        val externalMotorInitStateIntent: Observable<MenuIntent> = mExternalMotorInteractor.getExternalMotorInitStateIntent()
        val externalMotorToggleStateIntent: Observable<MenuIntent> = mExternalMotorInteractor.getExternalMotorToggleStateIntent()

        val externalPowerSupplyInitStateIntent: Observable<MenuIntent> = mExternalPowerSupplyInteractor.getExternalPowerSupplyInitState()

        val allIntents = Observable.mergeArray(
                menuButtonInitStateIntent,
                previousMenuButtonIntent,
                nextMenuButtonIntent,
                backMenuButtonIntent,
                confirmMenuButtonIntent,
                textToSpeechInitStateIntent,
                textToSpeechConvertStateIntent,
                speechToTextInitStateIntent,
                speechToTextConvertStateIntent,
                externalLedInitStateIntent,
                externalLedToggleStateIntent,
                externalMotorInitStateIntent,
                externalMotorToggleStateIntent,
                externalPowerSupplyInitStateIntent
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

        mTextToSpeechInteractor.release()
        mSpeechToTextInteractor.release()
        mExternalLedInteractor.release()
        mExternalMotorInteractor.release()
        mExternalPowerSupplyInteractor.release()
    }
}