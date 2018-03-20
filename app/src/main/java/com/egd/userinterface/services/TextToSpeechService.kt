package com.egd.userinterface.services

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * Singleton, used to manage the text to speech functionality. Internally
 * it uses [TextToSpeech] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
class TextToSpeechService(
        context: Context,
        language: Locale,
        pitch: Float,
        speechRate: Float
) : ITextToSpeechService {

    private var mTextToSpeech: TextToSpeech? = null
    private var mIsInit = false
    private val mInitState = BehaviorSubject.create<Unit>()

    init {
        Completable.create {
            mTextToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { state ->
                if (state == TextToSpeech.SUCCESS) {
                    mTextToSpeech!!.language = language
                    mTextToSpeech!!.setPitch(pitch)
                    mTextToSpeech!!.setSpeechRate(speechRate)
                    it.onComplete()
                } else {
                    Log.e(TAG, "TextToSpeech.OnInitListener() failed!")
                    it.onError(Throwable("ERROR"))
                }
            })
        }.doOnComplete {
            mIsInit = true
            mInitState.onNext(Unit)
        }.doOnError {
            mInitState.onError(it)
        }.observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun getInitState(): Observable<Unit> {
        return mInitState
    }

    override fun convertTextToSpeech(textToBeConverted: String): Observable<Unit> {
        return Observable.create {
            if (!mIsInit) {
                it.onError(Throwable("ERROR"))
            }

            val result = mTextToSpeech?.speak(textToBeConverted, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID)

            if (result == TextToSpeech.SUCCESS) {
                it.onComplete()
            } else {
                Log.e(TAG, "TextToSpeech.convertTextToSpeech() failed!")
                it.onError(Throwable("ERROR"))
            }
        }
    }

    override fun release() {
        mTextToSpeech?.shutdown()
        mInitState.onComplete()
    }

    companion object {

        private val TAG = TextToSpeechService::class.java.simpleName

        /**
         * Unique identifier, used only by [TextToSpeechService.convertTextToSpeech].
         */
        private const val UTTERANCE_ID = "UTTERANCE_ID"
    }
}
