package com.egd.userinterface.controllers;

import android.content.Context;
import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.utils.AsyncTaskUtil;

import java.io.File;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

/**
 * Singleton, used to manage the speech to text functionality. Internally
 * it uses {@link SpeechRecognizer} to realize the functionality.
 *
 * @author Petar Krastev
 * @since 5.11.2017
 */
public class SpeechToTextController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = SpeechToTextController.class.getSimpleName();

    private static SpeechToTextController sInstance;
    private SpeechRecognizer mSpeechRecognizer;

    /**
     * Specifies that the language model for the given search should be
     * every word from the dictionary.
     */
    private static final String FULL_DICTIONARY = "FULL_DICTIONARY";

    /**
     * Initializes the {@link SpeechRecognizer} by settings the acoustic model,
     * dictionary and language model of the English language.
     *
     * @param context {@link Context} reference
     */
    private SpeechToTextController(final Context context) {
        AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskListener<SpeechRecognizer>() {
            @Override
            public SpeechRecognizer onExecuteTask() throws Exception {
                Assets assets = new Assets(context);
                File assetDir = assets.syncAssets();

                // TODO: Move the string files to constants/provide a util for the mapping
                SpeechRecognizer speechRecognizer = SpeechRecognizerSetup.defaultSetup()
                        .setAcousticModel(
                                new File(assetDir, "en-us-ptm")
                        )
                        .setDictionary(
                                new File(assetDir, "cmudict-en-us.dict")
                        )
                        .getRecognizer();

                speechRecognizer.addNgramSearch(
                        FULL_DICTIONARY,
                        new File(assetDir, "en-us.lm.bin")
                );

                return speechRecognizer;
            }

            @Override
            public void onSuccess(SpeechRecognizer speechRecognizer) {
                if (speechRecognizer != null) {
                    // TODO: Give some feedback to the user that he can speak now
                    mSpeechRecognizer = speechRecognizer;
                    mSpeechRecognizer.addListener(new RecognitionListenerImplementation());
                    mSpeechRecognizer.startListening(FULL_DICTIONARY, Constants.SPEECH_TO_TEXT_TIMEOUT);
                } else {
                    // TODO: Implement some fallback
                }
            }

            @Override
            public void onError(Exception error) {
                // TODO: Implement some fallback
                Log.e(TAG, "SpeechToTextController.initialize() failed!", error);
            }
        });
    }

    /**
     * Initializes the {@link SpeechToTextController} instance. Internally it
     * initializes {@link SpeechRecognizer}. The initialization
     * is done asynchronously!
     *
     * @param context {@link Context} reference
     */
    public static void initialize(Context context) {
        sInstance = new SpeechToTextController(context);
    }

    /**
     * Expose the only instance of {@link SpeechToTextController}.
     *
     * @return The {@link SpeechToTextController} instance
     * @throws RuntimeException If {@link SpeechToTextController#initialize(Context)} is not called before this method
     */
    public static SpeechToTextController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call SpeechToTextController.initialize() first!");
        }

        return sInstance;
    }

    /**
     * Releases all resources held by the {@link SpeechToTextController}
     * class.
     */
    public void clear() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.shutdown();
            mSpeechRecognizer = null;
        }

        sInstance = null;
    }

    /**
     * Represents the implementation of {@link RecognitionListener}. A private instance is
     * maintained so that we do not expose the listener.
     */
    private class RecognitionListenerImplementation implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech() {
            // TODO
            Log.i(TAG, "SpeechToTextController.onBeginningOfSpeech()");
        }

        @Override
        public void onEndOfSpeech() {
            // TODO
            Log.i(TAG, "SpeechToTextController.onEndOfSpeech()");
        }

        @Override
        public void onPartialResult(Hypothesis hypothesis) {
            // TODO
            Log.i(TAG, "SpeechToTextController.onPartialResult()");
        }

        @Override
        public void onResult(Hypothesis hypothesis) {
            // TODO
            Log.i(TAG, "SpeechToTextController.onResult()");
        }

        @Override
        public void onError(Exception e) {
            // TODO
            Log.i(TAG, "SpeechToTextController.onError()", e);
        }

        @Override
        public void onTimeout() {
            // TODO
            Log.i(TAG, "SpeechToTextController.onTimeout()");
        }
    }
}
