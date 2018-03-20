package com.egd.userinterface.interactors

import com.egd.userinterface.bus.EventBus
import com.egd.userinterface.bus.events.SpeechToTextConvertEvent
import com.egd.userinterface.interactors.interfaces.ISpeechToTextInteractor
import com.egd.userinterface.services.interfaces.ISpeechToTextService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by User on 27.2.2018 г..
 */
class SpeechToTextInteractor(
        speechToTextService: ISpeechToTextService
) : ISpeechToTextInteractor {

    private val mSpeechToTextService = speechToTextService

    override fun getSpeechToTextInitIntent(): Observable<MenuIntent> {
        return mSpeechToTextService.getInitState()
                .map<MenuIntent> {
                    MenuIntent.SpeechToTextInitSuccessIntent
                }.onErrorReturnItem(
                        MenuIntent.SpeechToTextInitErrorIntent
                )
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSpeechToTextConvertIntent(): Observable<MenuIntent> {
        return EventBus.listen(SpeechToTextConvertEvent::class.java)
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
    }

    override fun release() {
        mSpeechToTextService.release()
    }
}