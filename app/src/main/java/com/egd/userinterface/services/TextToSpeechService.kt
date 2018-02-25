package com.egd.userinterface.services

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.egd.userinterface.event.bus.EventBus
import com.egd.userinterface.event.bus.events.TextToSpeechConvertErrorEvent
import com.egd.userinterface.event.bus.events.TextToSpeechInitializationEvent
import com.egd.userinterface.services.models.ITextToSpeechService
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

    private var mIsInitialized: Boolean = false
    private var mTextToSpeech: TextToSpeech? = null

    init {
        mTextToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                mTextToSpeech!!.language = language
                mTextToSpeech!!.setPitch(pitch)
                mTextToSpeech!!.setSpeechRate(speechRate)
                mIsInitialized = true
                EventBus.publish(
                        TextToSpeechInitializationEvent(true)
                )
            } else {
                Log.e(TAG, "TextToSpeech.OnInitListener() failed!")
                EventBus.publish(
                        TextToSpeechInitializationEvent(false)
                )
            }
        })
    }

    override fun convertTextToSpeech(textToBeConverted: String) {
        if (mIsInitialized) {
            val result = mTextToSpeech?.speak(textToBeConverted, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID)

            if (result == TextToSpeech.ERROR) {
                Log.e(TAG, "TextToSpeech.convertTextToSpeech() failed!")
                EventBus.publish(
                        TextToSpeechConvertErrorEvent()
                )
            }
        }
    }

    override fun release() {
        mTextToSpeech?.shutdown()
    }

    companion object {

        private val TAG = TextToSpeechService::class.java.simpleName

        /**
         * Unique identifier, used only by [TextToSpeechService.convertTextToSpeech].
         */
        private const val UTTERANCE_ID = "UTTERANCE_ID"
    }
}
