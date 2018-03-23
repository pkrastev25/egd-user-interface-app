package com.egd.userinterface.services

import android.content.Context
import android.util.Log
import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import com.egd.userinterface.services.interfaces.ISpeechToTextService
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.utils.SpeechRecognitionUtil
import edu.cmu.pocketsphinx.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * @author Petar Krastev
 */
class SpeechToTextService(
        context: Context,
        textToSpeechService: ITextToSpeechService
) : ISpeechToTextService {

    private val mContext = context.applicationContext
    private val mTextToSpeechService = textToSpeechService
    private var mSpeechRecognizer: SpeechRecognizer? = null
    private var mIsInit = false
    private var mIsActive = false
    private val mInitState = BehaviorSubject.create<Unit>()
    private lateinit var mSpeechRecognitionSubject: PublishSubject<Unit>
    private val mKeywordTranslationToKeywordDefinitionMap = SpeechRecognitionUtil.getKeywordTranslationToKeywordDefinitionMap(this.mContext!!)

    init {
        Completable.create {
            initializeTextToSpeechComponent()
            it.onComplete()
        }.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onError = {
                            Log.e(TAG, "SpeechToTextService.init() failed!", it)
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

    override fun recognizeSpeech(@SpeechRecognitionTypesAnnotation speechRecognitionType: String): Observable<Unit> {
        if (!mIsInit || mIsActive) {
            return Observable.error(
                    Throwable(
                            mContext.getString(R.string.speech_recognition_error)
                    )
            )
        }

        mIsActive = true
        mSpeechRecognitionSubject = PublishSubject.create<Unit>()

        convertTextToSpeech(
                mContext.getString(R.string.speech_recognition_feedback_start)
        )
        mSpeechRecognizer?.startListening(speechRecognitionType, Constants.SPEECH_TO_TEXT_TIMEOUT)

        return mSpeechRecognitionSubject
    }

    override fun release() {
        mSpeechRecognizer?.let {
            it.cancel()
            it.shutdown()
        }
        mInitState.onComplete()
    }

    private fun initializeTextToSpeechComponent() {
        val assets = Assets(mContext)
        val assetDir = assets.syncAssets()
        // Adjust the language according to the locale
        val locale = Locale.US

        // Initialize the recognizer, set the acoustic model and the dictionary
        val speechRecognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(
                        SpeechRecognitionUtil.getAcousticModelForLocale(assetDir, locale)
                )
                .setDictionary(
                        SpeechRecognitionUtil.getDictionaryForLocale(assetDir, locale)
                )
                .recognizer

        // Include a search option for the keywords
        speechRecognizer.addKeywordSearch(
                SpeechRecognitionTypesEnum.ALL_KEYWORDS,
                SpeechRecognitionUtil.getAssetsForKeywordAndLocale(assetDir, SpeechRecognitionTypesEnum.ALL_KEYWORDS, locale)
        )

        // Include a search option for the find keyword context
        speechRecognizer.addGrammarSearch(
                SpeechRecognitionTypesEnum.FIND_KEYWORD,
                SpeechRecognitionUtil.getAssetsForKeywordAndLocale(assetDir, SpeechRecognitionTypesEnum.FIND_KEYWORD, locale)
        )

        // Include a search option for the navigate keyword context
        speechRecognizer.addGrammarSearch(
                SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD,
                SpeechRecognitionUtil.getAssetsForKeywordAndLocale(assetDir, SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD, locale)
        )

        // Include a search option for the test keyword context
        speechRecognizer.addNgramSearch(
                SpeechRecognitionTypesEnum.TEST_KEYWORD,
                SpeechRecognitionUtil.getAssetsForKeywordAndLocale(assetDir, SpeechRecognitionTypesEnum.TEST_KEYWORD, locale)
        )

        if (speechRecognizer != null) {
            mSpeechRecognizer = speechRecognizer
            mSpeechRecognizer?.addListener(RecognitionListenerImplementation())
        } else {
            throw Throwable(
                    mContext.getString(R.string.speech_recognition_error_init)
            )
        }
    }

    private fun convertTextToSpeech(message: String) {
        mTextToSpeechService.convertTextToSpeech(
                message
        ).doOnError {
            mSpeechRecognitionSubject.onError(it)
        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe()
    }

    private fun stopSpeechRecognitionObservableStream() {
        mSpeechRecognitionSubject.onNext(Unit)
        mSpeechRecognitionSubject.onComplete()
    }

    private inner class RecognitionListenerImplementation : RecognitionListener {

        override fun onBeginningOfSpeech() {
            // Do nothing, for now...
        }

        override fun onEndOfSpeech() {
            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS == mSpeechRecognizer?.searchName) {
                mSpeechRecognizer?.stop()
            }
        }

        override fun onPartialResult(hypothesis: Hypothesis?) {
            val partialResult = hypothesis?.hypstr ?: ""

            if (mKeywordTranslationToKeywordDefinitionMap[partialResult] != null) {
                mSpeechRecognizer?.stop()
            }
        }

        override fun onResult(hypothesis: Hypothesis?) {
            mIsActive = false

            if (hypothesis == null) {
                convertTextToSpeech(
                        mContext.getString(R.string.speech_recognition_feedback_no_result)
                )
                stopSpeechRecognitionObservableStream()

                return
            }

            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS == mSpeechRecognizer?.searchName) {
                if (mKeywordTranslationToKeywordDefinitionMap[hypothesis.hypstr] != null) {
                    mIsActive = true
                    convertTextToSpeech(
                            mContext.getString(R.string.speech_recognition_feedback_recognized_keyword)
                    )
                    mSpeechRecognizer?.startListening(
                            mKeywordTranslationToKeywordDefinitionMap[hypothesis.hypstr],
                            Constants.SPEECH_TO_TEXT_TIMEOUT
                    )
                } else {
                    convertTextToSpeech(
                            mContext.getString(R.string.speech_recognition_feedback_no_result)
                    )
                    stopSpeechRecognitionObservableStream()
                }
            } else {
                // In the next version, invoke the appropriate service which handles the input command
                convertTextToSpeech(
                        hypothesis.hypstr
                )
                stopSpeechRecognitionObservableStream()
            }
        }

        override fun onError(e: Exception) {
            Log.e(TAG, "SpeechToTextService.onError()", e)
            mIsActive = false
            mSpeechRecognitionSubject.onError(
                    Throwable(
                            mContext.getString(R.string.speech_recognition_error)
                    )
            )
        }

        override fun onTimeout() {
            mSpeechRecognizer?.stop()
        }
    }

    companion object {
        private val TAG = SpeechToTextService::class.java.simpleName
    }
}
