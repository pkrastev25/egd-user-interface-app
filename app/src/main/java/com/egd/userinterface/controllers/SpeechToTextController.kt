package com.egd.userinterface.controllers

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.util.Log

import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.enums.InputGPIOEdgeTriggerTypesEnum
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnumAnnotation
import com.egd.userinterface.controllers.models.ISpeechToTextController
import com.egd.userinterface.utils.GPIOUtil
import com.egd.userinterface.utils.SpeechRecognitionUtil
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback

import java.io.IOException
import java.util.Locale

import edu.cmu.pocketsphinx.Assets
import edu.cmu.pocketsphinx.Hypothesis
import edu.cmu.pocketsphinx.RecognitionListener
import edu.cmu.pocketsphinx.SpeechRecognizer
import edu.cmu.pocketsphinx.SpeechRecognizerSetup

/**
 * Singleton, used to manage the speech to text functionality. Internally
 * it uses [SpeechRecognizer] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 5.11.2017
 */
class SpeechToTextController
/**
 * Initializes the [SpeechRecognizer] by settings the acoustic model,
 * dictionary and language model of the given language. Configures a pin
 * as input according to [Constants.SPEECH_TO_TEXT_INPUT].
 * Includes a debouncing mechanism for the inputs which ignores all incoming
 * interrupts for [Constants.GPIO_CALLBACK_SAMPLE_TIME_MS] after successfully
 * detecting the 1st interrupt. Greatly improves performance!
 *
 * @param context [Context] reference
 */
private constructor(private var mContext: Context?) : ISpeechToTextController {

    private var mSpeechRecognizer: SpeechRecognizer? = null

    // INPUT/OUTPUT helpers
    private var mInput: Gpio? = null
    private var mInputCallback: GpioCallback? = null
    private var mShouldDetectEdge: Boolean = false

    // STATE helpers
    private var mIsInitialized: Boolean = false
    private var mIsActive: Boolean = false

    /**
     * Map where the key is the translation of the given [SpeechRecognitionTypesEnum]
     * type and the value is the [SpeechRecognitionTypesEnum] type itself.
     */
    private val mKeywordContainer: Map<String, String>

    init {
        mShouldDetectEdge = true
        mKeywordContainer = SpeechRecognitionUtil.mapWordsToSpeechRecognitionTypes(this.mContext!!)

        mInputCallback = object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio?): Boolean {
                if (mShouldDetectEdge) {
                    mShouldDetectEdge = false

                    recognizeSpeech(SpeechRecognitionTypesEnum.ALL_KEYWORDS)

                    Handler().postDelayed({ mShouldDetectEdge = true }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS.toLong())
                }

                return true
            }

            override fun onGpioError(gpio: Gpio?, error: Int) {
                Log.e(TAG, "GpioCallback.onGpioError() called!")
                super.onGpioError(gpio, error)
            }
        }

        try {
            mInput = GPIOUtil.configureInputGPIO(
                    Constants.SPEECH_TO_TEXT_INPUT,
                    true,
                    InputGPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback as GpioCallback
            )
        } catch (e: IOException) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO()", e)
        }

        try {
            // TODO: Start a thread using RxJava!
            val assets = Assets(mContext)
            val assetDir = assets.syncAssets()
            // Adjust the language according to the locale
            val locale = Locale.getDefault()

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

            // Successful
            if (speechRecognizer != null) {
                mSpeechRecognizer = speechRecognizer
                mSpeechRecognizer!!.addListener(RecognitionListenerImplementation())
                mIsInitialized = true
                // Give the user feedback if the module is successfully started
                TextToSpeechController.instance.speak(
                        mContext!!.getString(R.string.speech_recognition_feedback_initialization_of_module)
                )
            } else {
                // Give the user feedback that the module failed to initialize
                TextToSpeechController.instance.speak(
                        mContext!!.getString(R.string.error_handling_occurred_error)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "SpeechToTextController.init() failed!", e)
            // Give the user feedback that the module failed to initialize
            TextToSpeechController.instance.speak(
                    mContext!!.getString(R.string.error_handling_occurred_error)
            )
        }
    }

    /**
     * Attempts to convert the speech input by the user into an equivalent
     * text format.
     *
     * @param type The type of content, the recognizer needs to do, must be one of type [SpeechRecognitionTypesEnum]
     */
    override fun recognizeSpeech(@SpeechRecognitionTypesEnumAnnotation type: String) {
        if (mIsInitialized && !mIsActive) {
            synchronized(SpeechToTextController::class.java) {
                if (mIsInitialized && !mIsActive) {
                    mIsActive = true
                    // Give feedback to the user so that he can start speaking
                    TextToSpeechController.instance.speak(
                            mContext!!.getString(R.string.speech_recognition_feedback_start_of_module)
                    )
                    mSpeechRecognizer!!.startListening(type, Constants.SPEECH_TO_TEXT_TIMEOUT)
                }
            }
        }
    }

    /**
     * Releases all resources held by the [SpeechToTextController]
     * class.
     */
    override fun clean() {
        if (sInstance == null) {
            synchronized(this) {
                if (sInstance == null) {
                    if (mSpeechRecognizer != null) {
                        mSpeechRecognizer!!.cancel()
                        mSpeechRecognizer!!.shutdown()
                        mSpeechRecognizer = null
                    }

                    try {
                        mInput!!.unregisterGpioCallback(mInputCallback)
                        mInput!!.close()
                    } catch (e: Exception) {
                        Log.e(TAG, "SpeechToTextController.clean() failed!", e)
                    }

                    mInputCallback = null
                    mInput = null
                    mContext = null
                    sInstance = null
                }
            }
        }
    }

    /**
     * Represents the implementation of [RecognitionListener]. A private instance is
     * maintained so that we do not expose the listener.
     */
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
            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS != mSpeechRecognizer!!.searchName) {
                mSpeechRecognizer!!.stop()
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
            val partialResult = if (hypothesis != null && !TextUtils.isEmpty(hypothesis.hypstr))
                hypothesis.hypstr
            else
                ""

            if (mKeywordContainer[partialResult] != null) {
                mSpeechRecognizer!!.stop()
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
                // Give feedback to the user that nothing was understood
                TextToSpeechController.instance.speak(
                        mContext!!.getString(R.string.speech_recognition_feedback_no_result)
                )

                return
            }

            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS == mSpeechRecognizer!!.searchName) {
                if (mKeywordContainer[hypothesis.hypstr] != null) {
                    mIsActive = true
                    // Give feedback to the user that the keyword has been understood
                    TextToSpeechController.instance.speak(
                            mContext!!.getString(R.string.speech_recognition_feedback_recognized_keyword)
                    )
                    mSpeechRecognizer!!.startListening(mKeywordContainer[hypothesis.hypstr], Constants.SPEECH_TO_TEXT_TIMEOUT)
                } else {
                    // Give feedback to the user that nothing was understood
                    TextToSpeechController.instance.speak(
                            mContext!!.getString(R.string.speech_recognition_feedback_no_result)
                    )
                }
            } else {
                // TODO: In the next version, invoke the appropriate service which handles the input command
                TextToSpeechController.instance.speak(hypothesis.hypstr)
            }
        }

        /**
         * Called when an error has occurred with the [SpeechRecognizer].
         *
         * @param e [Exception] which caused the [SpeechRecognizer] to stop
         */
        override fun onError(e: Exception) {
            Log.e(TAG, "SpeechToTextController.onError()", e)
            mIsActive = false
            // Give feedback to the user that an error has occurred
            TextToSpeechController.instance.speak(
                    mContext!!.getString(R.string.error_handling_occurred_error)
            )
        }

        /**
         * Called after [Constants.SPEECH_TO_TEXT_TIMEOUT] has elapsed and the user
         * has not said anything. The [SpeechRecognizer] is stopped and
         * [.onResult] is being called.
         */
        override fun onTimeout() {
            mSpeechRecognizer!!.stop()
        }
    }

    companion object {

        /**
         * Represents the class name, used only for debugging.
         */
        private val TAG = SpeechToTextController::class.java.simpleName
        private var sInstance: ISpeechToTextController? = null

        /**
         * Initializes the [SpeechToTextController] instance. Internally it
         * initializes [SpeechRecognizer]. The initialization
         * is done asynchronously!
         *
         * @param context [Context] reference
         */
        fun init(context: Context) {
            if (sInstance == null) {
                synchronized(SpeechToTextController::class.java) {
                    if (sInstance == null) {
                        sInstance = SpeechToTextController(context)
                    }
                }
            }
        }

        /**
         * Expose the only instance of [SpeechToTextController].
         *
         * @return The [SpeechToTextController] instance
         * @throws RuntimeException If [SpeechToTextController.init] is not called before this method
         */
        val instance: ISpeechToTextController
            get() {
                if (sInstance == null) {
                    throw RuntimeException("You must call SpeechToTextController.init() first!")
                }

                return sInstance as ISpeechToTextController
            }
    }
}
