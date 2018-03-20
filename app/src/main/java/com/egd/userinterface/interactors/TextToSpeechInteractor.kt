package com.egd.userinterface.interactors

import com.egd.userinterface.bus.EventBus
import com.egd.userinterface.bus.events.TextToSpeechConvertEvent
import com.egd.userinterface.interactors.interfaces.ITextToSpeechInteractor
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by User on 27.2.2018 г..
 */
class TextToSpeechInteractor(
        textToSpeechService: ITextToSpeechService
) : ITextToSpeechInteractor {

    private val mTextToSpeechService = textToSpeechService

    override fun getTextToSpeechInitIntent(): Observable<MenuIntent> {
        return mTextToSpeechService.getInitState()
                .map<MenuIntent> {
                    MenuIntent.TextToSpeechInitSuccessIntent
                }
                .onErrorReturnItem(
                        MenuIntent.TextToSpeechInitErrorIntent
                )
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTextToSpeechConvertIntent(): Observable<MenuIntent> {
        return EventBus.listen(TextToSpeechConvertEvent::class.java)
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            it.textToBeConverted
                    )
                }.flatMap {
                    Observable.empty<MenuIntent>()
                }.onErrorReturn {
                    MenuIntent.TextToSpeechConvertErrorIntent(
                            error = it
                    )
                }
    }

    override fun release() {
        mTextToSpeechService.release()
    }
}