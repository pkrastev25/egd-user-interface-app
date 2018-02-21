package com.egd.userinterface.controllers

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log

import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.controllers.models.IMotorController
import com.egd.userinterface.controllers.models.ITextToSpeechController

import java.util.LinkedList
import java.util.Locale
import java.util.Queue

/**
 * Singleton, used to manage the text to speech functionality. Internally
 * it uses [TextToSpeech] to realize the functionality.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
class TextToSpeechController
/**
 * Initializes the [TextToSpeech]. Upon successful initialization, it
 * executes all pending [TextToSpeechController.speak] operations,
 * if there are any.
 *
 * @param context    [Context] reference
 * @param language   Specifies the language for the [TextToSpeech]
 * @param pitch      Specifies the pitch of the voice for the [TextToSpeech], where a higher number has a higher pitch and vice versa
 * @param speechRate Specifies the speech rate of the voice for the [TextToSpeech], where a higher number has a higher speech rate and vice versa
 */
private constructor(context: Context, language: Locale, pitch: Float, speechRate: Float) : ITextToSpeechController {

    /**
     * Used to store the text of all pending
     * [TextToSpeechController.speak] operations.
     */
    private var mPendingOperations: Queue<String>? = null

    /**
     * Used to check if the [TextToSpeech] is already
     * initialized.
     */
    private var mIsInitialized: Boolean = false
    private var mTextToSpeech: TextToSpeech? = null

    /**
     * Used to give the user haptic feedback if the [TextToSpeechController]
     * fails to initialize.
     */
    private var mMotorController: IMotorController? = null

    init {
        mPendingOperations = LinkedList()
        // Give the user feedback if the module is successfully started
        mPendingOperations!!.add(
                context.getString(R.string.text_to_speech_initialization_of_module)
        )

        mMotorController = MotorController(
                context,
                Constants.MOTOR_GPIO_INPUT,
                Constants.MOTOR_GPIO_OUTPUT,
                Constants.MOTOR_PWM_DUTY_CYCLE,
                Constants.MOTOR_PWM_FREQUENCY
        )

        mTextToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                mTextToSpeech!!.language = language
                mTextToSpeech!!.setPitch(pitch)
                mTextToSpeech!!.setSpeechRate(speechRate)
                mIsInitialized = true
                executePendingOperations()
            } else {
                Log.e(TAG, "TextToSpeech.OnInitListener() failed!")
                // Give the user feedback that the module failed to initialize
                mMotorController!!.start()
            }
        })
    }

    /**
     * Converts text to a speech output. If it happens that the
     * [TextToSpeech] instance is not initialized yet, it stores
     * all text outputs into a queue. Once the [TextToSpeech]
     * instance is initialized, all pending text outputs from the queue
     * are converted to speech.
     *
     * @param output Text to be read for the user
     */
    override fun speak(output: String) {
        if (mIsInitialized) {
            synchronized(TextToSpeechController::class.java) {
                if (mIsInitialized) {
                    val result = mTextToSpeech!!.speak(output, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID)

                    if (result == TextToSpeech.ERROR) {
                        Log.e(TAG, "TextToSpeech.speak() failed!")
                        mMotorController!!.start()
                    } else {
                        // TODO: Fix?
                    }
                } else {
                    mPendingOperations!!.add(output)
                }
            }
        } else {
            mPendingOperations!!.add(output)
        }
    }

    /**
     * Releases all resources held by the [TextToSpeechController]
     * class.
     */
    override fun clean() {
        if (sInstance == null) {
            synchronized(this) {
                if (sInstance == null) {
                    if (mTextToSpeech != null) {
                        mTextToSpeech!!.shutdown()
                        mTextToSpeech = null
                    }

                    if (mMotorController != null) {
                        mMotorController!!.clean()
                        mMotorController = null
                    }

                    mPendingOperations = null
                    sInstance = null
                }
            }
        }
    }

    /**
     * Executes all pending [TextToSpeechController.speak]
     * operations, if there are any.
     */
    private fun executePendingOperations() {
        while (!mPendingOperations!!.isEmpty()) {
            speak(mPendingOperations!!.remove())
        }
    }

    companion object {

        /**
         * Represents the class name, used only for debugging.
         */
        private val TAG = TextToSpeechController::class.java.simpleName

        /**
         * Unique identifier, used only by [TextToSpeechController.speak].
         */
        private val UTTERANCE_ID = "UTTERANCE_ID"
        private var sInstance: ITextToSpeechController? = null

        /**
         * Initializes the [TextToSpeechController] instance. Internally it
         * initializes [TextToSpeech]. The initialization
         * is done asynchronously!
         *
         * @param context [Context] reference
         */
        fun init(context: Context) {
            if (sInstance == null) {
                synchronized(TextToSpeechController::class.java) {
                    if (sInstance == null) {
                        sInstance = TextToSpeechController(
                                context,
                                Constants.TEXT_TO_SPEECH_DEFAULT_LANGUAGE,
                                Constants.TEXT_TO_SPEECH_DEFAULT_PITCH,
                                Constants.TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE
                        )
                    }
                }
            }
        }

        /**
         * Expose the only instance of [TextToSpeechController].
         *
         * @return The [TextToSpeechController] instance
         * @throws RuntimeException If [TextToSpeechController.init] is not called before this method
         */
        val instance: ITextToSpeechController
            get() {
                if (sInstance == null) {
                    throw RuntimeException("You must call TextToSpeechController.init() first!")
                }

                return sInstance as ITextToSpeechController
            }
    }
}
