package com.egd.userinterface.services

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * @author Petar Krastev
 */
class TextToSpeechService(
        context: Context,
        language: Locale,
        pitch: Float,
        speechRate: Float
) : ITextToSpeechService {

    private val mContext = context.applicationContext
    private var mTextToSpeech: TextToSpeech? = null
    private var mIsInit = false
    private val mInitState = BehaviorSubject.create<Unit>()

    init {
        Completable.create {
            mTextToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { state ->
                if (state == TextToSpeech.SUCCESS) {
                    mTextToSpeech?.let {
                        it.language = language
                        it.setPitch(pitch)
                        it.setSpeechRate(speechRate)
                    }
                    it.onComplete()
                } else {
                    Log.e(TAG, "TextToSpeechService init failed!")
                    it.onError(
                            Throwable(
                                    mContext.getString(R.string.text_to_speech_error_init)
                            )
                    )
                }
            })
        }.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onError = {
                            mInitState.onError(it)
                        },
                        onComplete = {
                            mIsInit = true
                            mInitState.onNext(Unit)
                        }
                )
    }

    override fun getInitState(): Observable<Unit> {
        return mInitState
    }

    override fun convertTextToSpeech(textToBeConverted: String): Observable<Unit> {
        return Observable.create {
            if (!mIsInit) {
                it.onError(
                        Throwable(
                                mContext.getString(R.string.text_to_speech_error)
                        )
                )
            }

            val result = mTextToSpeech?.speak(textToBeConverted, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID)

            if (result == TextToSpeech.SUCCESS) {
                it.onComplete()
            } else {
                Log.e(TAG, "convertTextToSpeech failed!")
                it.onError(
                        Throwable(
                                mContext.getString(R.string.text_to_speech_error)
                        )
                )
            }
        }
    }

    override fun release() {
        mTextToSpeech?.shutdown()
        mInitState.onComplete()
    }

    companion object {

        private val TAG = TextToSpeechService::class.java.simpleName

        private const val UTTERANCE_ID = "UTTERANCE_ID"
    }
}
