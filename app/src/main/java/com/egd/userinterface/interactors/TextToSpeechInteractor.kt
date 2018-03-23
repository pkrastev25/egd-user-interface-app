package com.egd.userinterface.interactors

import com.egd.userinterface.buses.ReducerInteractorCommunicationBus
import com.egd.userinterface.buses.events.TextToSpeechConvertEvent
import com.egd.userinterface.interactors.interfaces.ITextToSpeechInteractor
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author Petar Krastev
 */
class TextToSpeechInteractor(
        textToSpeechService: ITextToSpeechService
) : ITextToSpeechInteractor {

    private val mTextToSpeechService = textToSpeechService

    override fun getTextToSpeechInitStateIntent(): Observable<MenuIntent> {
        return mTextToSpeechService.getInitState()
                .map<MenuIntent> {
                    MenuIntent.TextToSpeechInitSuccessIntent
                }
                .onErrorReturnItem(
                        MenuIntent.TextToSpeechInitErrorIntent
                )
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTextToSpeechConvertStateIntent(): Observable<MenuIntent> {
        return ReducerInteractorCommunicationBus.listen(TextToSpeechConvertEvent::class.java)
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            it.textToBeConverted
                    )
                }.flatMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.TextToSpeechConvertErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun release() {
        mTextToSpeechService.release()
    }
}