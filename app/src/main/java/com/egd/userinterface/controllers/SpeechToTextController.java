package com.egd.userinterface.controllers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerType;
import com.egd.userinterface.controllers.models.ISpeechToTextController;
import com.egd.userinterface.utils.AsyncTaskUtil;
import com.egd.userinterface.utils.GPIOUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.File;
import java.io.IOException;

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
public class SpeechToTextController implements ISpeechToTextController {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = SpeechToTextController.class.getSimpleName();

    /**
     * Makes a connection between this string definition and a language
     * model search options in en-us.lm.bin. This constant is used mainly
     * to tell the recognizer for what should he search.
     */
    private static final String LANGUAGE_MODEL_EN = "LANGUAGE_MODEL_EN";

    /**
     * Makes a connection between this string definition and grammar search
     * options in options.gram. This constant is used mainly to tell the
     * recognizer for what should he search.
     */
    private static final String GRAMMAR_OPTIONS = "GRAMMAR_OPTIONS";
    private static ISpeechToTextController sInstance;

    private SpeechRecognizer mSpeechRecognizer;
    private Gpio mInput;
    private GpioCallback mInputCallback;

    /**
     * Initializes the {@link SpeechRecognizer} by settings the acoustic model,
     * dictionary and language model of the English language. Configures a pin
     * as input according to {@link Constants#SPEECH_TO_TEXT_INPUT}.
     *
     * @param context {@link Context} reference
     */
    private SpeechToTextController(final Context context) {
        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                recognizeSpeech();

                return true;
            }

            @Override
            public void onGpioError(Gpio gpio, int error) {
                Log.e(TAG, "GpioCallback.onGpioError() called!");
                super.onGpioError(gpio, error);
            }
        };

        mInput = GPIOUtil.configureInputGPIO(
                Constants.SPEECH_TO_TEXT_INPUT,
                true,
                GPIOEdgeTriggerType.EDGE_RISING,
                mInputCallback
        );

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

                speechRecognizer.addGrammarSearch(
                        GRAMMAR_OPTIONS,
                        new File(assetDir, "options.gram")
                );

                speechRecognizer.addNgramSearch(
                        LANGUAGE_MODEL_EN,
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
    public static ISpeechToTextController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call SpeechToTextController.initialize() first!");
        }

        return sInstance;
    }

    /**
     * Attempts to convert the speech input by the user into an equivalent
     * text format.
     */
    @Override
    public void recognizeSpeech() {
        mSpeechRecognizer.startListening(GRAMMAR_OPTIONS, Constants.SPEECH_TO_TEXT_TIMEOUT);
    }

    /**
     * Releases all resources held by the {@link SpeechToTextController}
     * class.
     */
    @Override
    public void clean() {
        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.shutdown();
            mSpeechRecognizer = null;
        }

        try {
            mInput.unregisterGpioCallback(mInputCallback);
            mInput.close();
        } catch (IOException e) {
            Log.e(TAG, "SpeechToTextController.clean() failed!", e);
        }

        mInputCallback = null;
        mInput = null;
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
            mSpeechRecognizer.stop();
        }

        @Override
        public void onPartialResult(Hypothesis hypothesis) {
            String partialResult = (hypothesis != null && !TextUtils.isEmpty(hypothesis.getHypstr()))
                    ? hypothesis.getHypstr()
                    : "";

            // TODO: This is used only for testing the natural language recognition, remove in a future version
            if ("test language model".equals(partialResult)) {
                mSpeechRecognizer.startListening(LANGUAGE_MODEL_EN, Constants.SPEECH_TO_TEXT_TIMEOUT);
            }
        }

        @Override
        public void onResult(Hypothesis hypothesis) {
            Log.i(TAG, "SpeechToTextController.onResult()");

            if (hypothesis != null && !TextUtils.isEmpty(hypothesis.getHypstr())) {
                Log.e(TAG, hypothesis.getHypstr());
                TextToSpeechController.getInstance().speak(hypothesis.getHypstr());
            } else {
                TextToSpeechController.getInstance().speak("I understood nothing!");
            }
        }

        @Override
        public void onError(Exception e) {
            // TODO
            Log.e(TAG, "SpeechToTextController.onError()", e);
        }

        @Override
        public void onTimeout() {
            Log.i(TAG, "SpeechToTextController.onTimeout()");
            mSpeechRecognizer.stop();
        }
    }
}
