package com.egd.userinterface.services

import android.content.Context
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnumAnnotation
import com.egd.userinterface.event.bus.EventBus
import com.egd.userinterface.event.bus.events.SpeechToTextInitializationEvent
import com.egd.userinterface.event.bus.events.TextToSpeechConvertEvent
import com.egd.userinterface.services.models.ISpeechToTextService
import com.egd.userinterface.utils.SpeechRecognitionUtil
import edu.cmu.pocketsphinx.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Singleton, used to manage the speech to text functionality. Internally
 * it uses [SpeechRecognizer] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 5.11.2017
 */
class SpeechToTextService(private var mContext: Context?) : ISpeechToTextService {

    private var mSpeechRecognizer: SpeechRecognizer? = null
    private var mIsInitialized = false
    private var mIsActive = false

    /**
     * Map where the key is the translation of the given [SpeechRecognitionTypesEnum]
     * type and the value is the [SpeechRecognitionTypesEnum] type itself.
     */
    private val mKeywordContainer = SpeechRecognitionUtil.mapWordsToSpeechRecognitionTypes(this.mContext!!)

    init {
        Observable.just(
                try {
                    val assets = Assets(mContext)
                    val assetDir = assets.syncAssets()
                    // Adjust the language according to the locale
                    val locale = Locale.US

                    // Initialize the recognizer, set the acoustic model and the dictionary
                    val speechRecognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(
                                    SpeechRecognitionUtil.getAcousticModel(assetDir, locale)
                            )
                            .setDictionary(
                                    SpeechRecognitionUtil.getDictionary(assetDir, locale)
                            )
                            .recognizer

                    // Include a search option for the keywords
                    speechRecognizer.addKeywordSearch(
                            SpeechRecognitionTypesEnum.ALL_KEYWORDS,
                            SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.ALL_KEYWORDS, locale)
                    )

                    // Include a search option for the find keyword context
                    speechRecognizer.addGrammarSearch(
                            SpeechRecognitionTypesEnum.FIND_KEYWORD,
                            SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.FIND_KEYWORD, locale)
                    )

                    // Include a search option for the navigate keyword context
                    speechRecognizer.addGrammarSearch(
                            SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD,
                            SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD, locale)
                    )

                    // Include a search option for the test keyword context
                    speechRecognizer.addNgramSearch(
                            SpeechRecognitionTypesEnum.TEST_KEYWORD,
                            SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.TEST_KEYWORD, locale)
                    )

                    if (speechRecognizer != null) {
                        mSpeechRecognizer = speechRecognizer
                        mSpeechRecognizer!!.addListener(RecognitionListenerImplementation())
                        mIsInitialized = true
                        EventBus.publish(
                                SpeechToTextInitializationEvent(true)
                        )
                    } else {
                        EventBus.publish(
                                SpeechToTextInitializationEvent(false)
                        )
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "SpeechToTextService.init() failed!", e)
                    EventBus.publish(
                            SpeechToTextInitializationEvent(false)
                    )
                }
        ).observeOn(Schedulers.io())
    }

    override fun recognizeSpeech(@SpeechRecognitionTypesEnumAnnotation speechRecognitionType: String) {
        if (mIsInitialized && !mIsActive) {
            mIsActive = true
            EventBus.publish(
                    TextToSpeechConvertEvent(mContext!!.getString(R.string.speech_recognition_feedback_start_of_module))
            )
            mSpeechRecognizer?.startListening(speechRecognitionType, Constants.SPEECH_TO_TEXT_TIMEOUT)
        }
    }

    override fun release() {
        mSpeechRecognizer?.cancel()
        mSpeechRecognizer?.shutdown()
        mContext = null
    }

    private inner class RecognitionListenerImplementation : RecognitionListener {

        /**
         * Called when the user has begun speaking.
         */
        override fun onBeginningOfSpeech() {
            // Do nothing, for now...
        }

        /**
         * Called when the user has stopped speaking. The [SpeechRecognizer] verifies
         * if the user has said any of the keywords. If that is the case, the [SpeechRecognizer]
         * is stopped  and [.onResult] is being called.
         */
        override fun onEndOfSpeech() {
            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS == mSpeechRecognizer?.searchName) {
                mSpeechRecognizer?.stop()
            }
        }

        /**
         * Called during the speech recognition process. If a keyword is detected, the
         * [SpeechRecognizer] is stopped and [.onResult] is
         * being called.
         *
         * @param hypothesis Contains the result of the [SpeechRecognizer]
         */
        override fun onPartialResult(hypothesis: Hypothesis?) {
            val partialResult = hypothesis?.hypstr ?: ""

            if (mKeywordContainer[partialResult] != null) {
                mSpeechRecognizer?.stop()
            }
        }

        /**
         * Called when the [SpeechRecognizer] has been stopped. If a keyword is detected,
         * the [SpeechRecognizer] starts listening for the context of the keyword. If no
         * keyword/speech is recognized, feedback is given to the user.
         *
         * @param hypothesis Contains the result of the [SpeechRecognizer]
         */
        override fun onResult(hypothesis: Hypothesis?) {
            mIsActive = false

            if (hypothesis == null) {
                EventBus.publish(
                        TextToSpeechConvertEvent(
                                mContext!!.getString(R.string.speech_recognition_feedback_no_result)
                        )
                )

                return
            }

            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS == mSpeechRecognizer?.searchName) {
                if (mKeywordContainer[hypothesis.hypstr] != null) {
                    mIsActive = true
                    EventBus.publish(
                            TextToSpeechConvertEvent(
                                    mContext!!.getString(R.string.speech_recognition_feedback_recognized_keyword)
                            )
                    )
                    mSpeechRecognizer?.startListening(
                            mKeywordContainer[hypothesis.hypstr],
                            Constants.SPEECH_TO_TEXT_TIMEOUT
                    )
                } else {
                    EventBus.publish(
                            TextToSpeechConvertEvent(
                                    mContext!!.getString(R.string.speech_recognition_feedback_no_result)
                            )
                    )
                }
            } else {
                // In the next version, invoke the appropriate service which handles the input command
                EventBus.publish(
                        TextToSpeechConvertEvent(
                                hypothesis.hypstr
                        )
                )
            }
        }

        /**
         * Called when an error has occurred with the [SpeechRecognizer].
         *
         * @param e [Exception] which caused the [SpeechRecognizer] to stop
         */
        override fun onError(e: Exception) {
            Log.e(TAG, "SpeechToTextService.onError()", e)
            mIsActive = false
        }

        /**
         * Called after [Constants.SPEECH_TO_TEXT_TIMEOUT] has elapsed and the user
         * has not said anything. The [SpeechRecognizer] is stopped and
         * [.onResult] is being called.
         */
        override fun onTimeout() {
            mSpeechRecognizer?.stop()
        }
    }

    companion object {
        private val TAG = SpeechToTextService::class.java.simpleName
    }
}
