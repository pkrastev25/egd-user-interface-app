package com.egd.userinterface.controllers;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.controllers.models.ITextToSpeechController;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/**
 * Singleton, used to manage the text to speech functionality. Internally
 * it uses {@link TextToSpeech} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 28.10.2017
 */
public class TextToSpeechController implements ITextToSpeechController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = TextToSpeechController.class.getSimpleName();

    /**
     * Unique identifier, used only by {@link TextToSpeechController#speak(String)}.
     */
    private static final String UTTERANCE_ID = "UTTERANCE_ID";
    private static ITextToSpeechController sInstance;

    /**
     * Used to store the text of all pending
     * {@link TextToSpeechController#speak(String)} operations.
     */
    private Queue<String> mPendingOperations;

    /**
     * Used to check if the {@link TextToSpeech} is already
     * initialized.
     */
    private boolean mIsInitialized;
    private TextToSpeech mTextToSpeech;

    /**
     * Initializes the {@link TextToSpeech}. Upon successful initialization, it
     * executes all pending {@link TextToSpeechController#speak(String)} operations,
     * if there are any.
     *
     * @param context    {@link Context} reference
     * @param language   Specifies the language for the {@link TextToSpeech}
     * @param pitch      Specifies the pitch of the voice for the {@link TextToSpeech}, where a higher number has a higher pitch and vice versa
     * @param speechRate Specifies the speech rate of the voice for the {@link TextToSpeech}, where a higher number has a higher speech rate and vice versa
     */
    private TextToSpeechController(Context context, final Locale language, final float pitch, final float speechRate) {
        mPendingOperations = new LinkedList<>();
        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(language);
                    mTextToSpeech.setPitch(pitch);
                    mTextToSpeech.setSpeechRate(speechRate);
                    mIsInitialized = true;
                    executePendingOperations();
                } else {
                    Log.e(TAG, "TextToSpeech.OnInitListener() failed!");
                    mTextToSpeech = null;
                    // TODO: Implement some fallback
                }
            }
        });
    }

    /**
     * Initializes the {@link TextToSpeechController} instance. Internally it
     * initializes {@link TextToSpeech}. The initialization
     * is done asynchronously!
     *
     * @param context {@link Context} reference
     */
    public static void initialize(Context context) {
        if (sInstance == null) {
            synchronized (TextToSpeechController.class) {
                if (sInstance == null) {
                    sInstance = new TextToSpeechController(
                            context,
                            Constants.TEXT_TO_SPEECH_DEFAULT_LANGUAGE,
                            Constants.TEXT_TO_SPEECH_DEFAULT_PITCH,
                            Constants.TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE
                    );
                }
            }
        }
    }

    /**
     * Expose the only instance of {@link TextToSpeechController}.
     *
     * @return The {@link TextToSpeechController} instance
     * @throws RuntimeException If {@link TextToSpeechController#initialize(Context)} is not called before this method
     */
    public static ITextToSpeechController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call TextToSpeechController.initialize() first!");
        }

        return sInstance;
    }

    /**
     * Converts text to a speech output. If it happens that the
     * {@link TextToSpeech} instance is not initialized yet, it stores
     * all text outputs into a queue. Once the {@link TextToSpeech}
     * instance is initialized, all pending text outputs from the queue
     * are converted to speech.
     *
     * @param output Text to be read for the user
     */
    @Override
    public void speak(String output) {
        synchronized (TextToSpeechController.class) {
            if (mIsInitialized) {
                int result = mTextToSpeech.speak(output, TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID);

                if (result == TextToSpeech.ERROR) {
                    Log.e(TAG, "TextToSpeech.speak() failed!");
                    // TODO: Implement some fallback
                }
            } else {
                mPendingOperations.add(output);
            }
        }
    }

    /**
     * Releases all resources held by the {@link TextToSpeechController}
     * class.
     */
    @Override
    public void clean() {
        if (mTextToSpeech != null) {
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }

        sInstance = null;
        mIsInitialized = false;
        mPendingOperations = null;
    }

    /**
     * Executes all pending {@link TextToSpeechController#speak(String)}
     * operations, if there are any.
     */
    private void executePendingOperations() {
        while (!mPendingOperations.isEmpty()) {
            speak(mPendingOperations.remove());
        }
    }
}
