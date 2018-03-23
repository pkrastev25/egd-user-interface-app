package com.egd.userinterface.interactors

import com.egd.userinterface.buses.ReducerInteractorCommunicationBus
import com.egd.userinterface.buses.events.SpeechToTextConvertEvent
import com.egd.userinterface.interactors.interfaces.ISpeechToTextInteractor
import com.egd.userinterface.services.interfaces.ISpeechToTextService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author Petar Krastev
 */
class SpeechToTextInteractor(
        speechToTextService: ISpeechToTextService
) : ISpeechToTextInteractor {

    private val mSpeechToTextService = speechToTextService

    override fun getSpeechToTextInitStateIntent(): Observable<MenuIntent> {
        return mSpeechToTextService.getInitState()
                .map<MenuIntent> {
                    MenuIntent.SpeechToTextInitSuccessIntent
                }.onErrorReturnItem(
                        MenuIntent.SpeechToTextInitErrorIntent
                )
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSpeechToTextConvertStateIntent(): Observable<MenuIntent> {
        return ReducerInteractorCommunicationBus.listen(SpeechToTextConvertEvent::class.java)
                .switchMap {
                    mSpeechToTextService.recognizeSpeech(
                            it.speechRecognitionType
                    )
                }.map<MenuIntent> {
                    MenuIntent.SpeechToTextStopConvertIntent
                }.onErrorReturn {
                    MenuIntent.SpeechToTextConvertErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun release() {
        mSpeechToTextService.release()
    }
}