package com.egd.userinterface.controllers;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.egd.userinterface.R;
import com.egd.userinterface.constants.Constants;
import com.egd.userinterface.constants.enums.GPIOEdgeTriggerTypesEnum;
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum;
import com.egd.userinterface.controllers.models.ISpeechToTextController;
import com.egd.userinterface.utils.AsyncTaskUtil;
import com.egd.userinterface.utils.GPIOUtil;
import com.egd.userinterface.utils.SpeechRecognitionUtil;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

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
    private static ISpeechToTextController sInstance;

    private SpeechRecognizer mSpeechRecognizer;
    private Context mContext;

    // INPUT/OUTPUT helpers
    private Gpio mInput;
    private GpioCallback mInputCallback;
    private boolean mShouldDetectEdge;

    // STATE helpers
    private boolean mIsInitialized;
    private boolean mIsActive;

    /**
     * Map where the key is the translation of the given {@link SpeechRecognitionTypesEnum}
     * type and the value is the {@link SpeechRecognitionTypesEnum} type itself.
     */
    private Map<String, String> mKeywordContainer;

    /**
     * Initializes the {@link SpeechRecognizer} by settings the acoustic model,
     * dictionary and language model of the English language. Configures a pin
     * as input according to {@link Constants#SPEECH_TO_TEXT_INPUT}.
     * Include a debouncing mechanism for the inputs which ignores all incoming
     * interrupts for {@link Constants#GPIO_CALLBACK_SAMPLE_TIME_MS} after successfully
     * detecting the 1st interrupt. Greatly improves performance!
     *
     * @param context {@link Context} reference
     */
    private SpeechToTextController(final Context context) {
        mContext = context;
        mKeywordContainer = SpeechRecognitionUtil.mapWordsToSpeechRecognitionTypes(context);

        mInputCallback = new GpioCallback() {
            @Override
            public boolean onGpioEdge(Gpio gpio) {
                if (mShouldDetectEdge) {
                    mShouldDetectEdge = false;

                    recognizeSpeech(SpeechRecognitionTypesEnum.ALL_KEYWORDS);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mShouldDetectEdge = true;
                        }
                    }, Constants.GPIO_CALLBACK_SAMPLE_TIME_MS);
                }

                return true;
            }

            @Override
            public void onGpioError(Gpio gpio, int error) {
                Log.e(TAG, "GpioCallback.onGpioError() called!");
                super.onGpioError(gpio, error);
            }
        };

        try {
            mInput = GPIOUtil.configureInputGPIO(
                    Constants.SPEECH_TO_TEXT_INPUT,
                    true,
                    GPIOEdgeTriggerTypesEnum.EDGE_RISING,
                    mInputCallback
            );
        } catch (IOException e) {
            Log.e(TAG, "GPIOUtil.configureInputGPIO()", e);
        }

        AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskListener<SpeechRecognizer>() {
            @Override
            public SpeechRecognizer onExecuteTask() throws Exception {
                Assets assets = new Assets(context);
                File assetDir = assets.syncAssets();
                Locale locale = Locale.getDefault();

                // Initialize the recognizer, set the acoustic model and the dictionary
                SpeechRecognizer speechRecognizer = SpeechRecognizerSetup.defaultSetup()
                        .setAcousticModel(
                                SpeechRecognitionUtil.getAcousticModel(assetDir, locale)
                        )
                        .setDictionary(
                                SpeechRecognitionUtil.getDictionary(assetDir, locale)
                        )
                        .getRecognizer();

                // Include a search option for the keywords
                speechRecognizer.addKeywordSearch(
                        SpeechRecognitionTypesEnum.ALL_KEYWORDS,
                        SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.ALL_KEYWORDS, locale)
                );

                // Include a search option for the find keyword context
                speechRecognizer.addGrammarSearch(
                        SpeechRecognitionTypesEnum.FIND_KEYWORD,
                        SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.FIND_KEYWORD, locale)
                );

                // Include a search option for the navigate keyword context
                speechRecognizer.addGrammarSearch(
                        SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD,
                        SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD, locale)
                );

                // Include a search option for the navigate keyword context
                speechRecognizer.addNgramSearch(
                        SpeechRecognitionTypesEnum.TEST_KEYWORD,
                        SpeechRecognitionUtil.getAssetsForKeyword(assetDir, SpeechRecognitionTypesEnum.TEST_KEYWORD, locale)
                );

                return speechRecognizer;
            }

            @Override
            public void onSuccess(SpeechRecognizer speechRecognizer) {
                if (speechRecognizer != null) {
                    mSpeechRecognizer = speechRecognizer;
                    mSpeechRecognizer.addListener(new RecognitionListenerImplementation());
                    mIsInitialized = true;
                    TextToSpeechController.getInstance().speak(
                            mContext.getString(R.string.speech_recognition_feedback_module_init_success)
                    );
                } else {
                    TextToSpeechController.getInstance().speak(
                            mContext.getString(R.string.speech_recognition_feedback_module_init_error)
                    );
                }
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG, "SpeechToTextController.init() failed!", error);
                TextToSpeechController.getInstance().speak(
                        mContext.getString(R.string.speech_recognition_feedback_module_init_error)
                );
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
    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (SpeechToTextController.class) {
                if (sInstance == null) {
                    sInstance = new SpeechToTextController(context);
                }
            }
        }
    }

    /**
     * Expose the only instance of {@link SpeechToTextController}.
     *
     * @return The {@link SpeechToTextController} instance
     * @throws RuntimeException If {@link SpeechToTextController#init(Context)} is not called before this method
     */
    public static ISpeechToTextController getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("You must call SpeechToTextController.init() first!");
        }

        return sInstance;
    }

    /**
     * Attempts to convert the speech input by the user into an equivalent
     * text format.
     *
     * @param type The type of content, the recognizer needs to do, must be one of type {@link SpeechRecognitionTypesEnum}
     */
    @Override
    public void recognizeSpeech(@SpeechRecognitionTypesEnum String type) {
        if (mIsInitialized && !mIsActive) {
            synchronized (SpeechToTextController.class) {
                if (mIsInitialized && !mIsActive) {
                    mIsActive = true;
                    mSpeechRecognizer.startListening(type, Constants.SPEECH_TO_TEXT_TIMEOUT);
                }
            }
        }
    }

    /**
     * Releases all resources held by the {@link SpeechToTextController}
     * class.
     */
    @Override
    public void clean() {
        if (sInstance == null) {
            synchronized (this) {
                if (sInstance == null) {
                    if (mSpeechRecognizer != null) {
                        mSpeechRecognizer.cancel();
                        mSpeechRecognizer.shutdown();
                        mSpeechRecognizer = null;
                    }

                    try {
                        mInput.unregisterGpioCallback(mInputCallback);
                        mInput.close();
                    } catch (Exception e) {
                        Log.e(TAG, "SpeechToTextController.clean() failed!", e);
                    }

                    mInputCallback = null;
                    mInput = null;
                    sInstance = null;
                    mContext = null;
                }
            }
        }
    }

    /**
     * Represents the implementation of {@link RecognitionListener}. A private instance is
     * maintained so that we do not expose the listener.
     */
    private class RecognitionListenerImplementation implements RecognitionListener {

        /**
         * Called when the user has begun speaking.
         */
        @Override
        public void onBeginningOfSpeech() {
            Log.i(TAG, "SpeechToTextController.onBeginningOfSpeech()");
        }

        /**
         * Called when the user has stopped speaking. The {@link SpeechRecognizer} verifies
         * if the user has said any of the keywords. If that is the case, the {@link SpeechRecognizer}
         * is stopped  and {@link #onResult(Hypothesis)} is being called.
         */
        @Override
        public void onEndOfSpeech() {
            Log.i(TAG, "SpeechToTextController.onEndOfSpeech()");

            if (!SpeechRecognitionTypesEnum.ALL_KEYWORDS.equals(mSpeechRecognizer.getSearchName())) {
                mSpeechRecognizer.stop();
            }
        }

        /**
         * Called during the speech recognition process. If a keyword is detected, the
         * {@link SpeechRecognizer} is stopped and {@link #onResult(Hypothesis)} is
         * being called.
         *
         * @param hypothesis Contains the result of the {@link SpeechRecognizer}
         */
        @Override
        public void onPartialResult(Hypothesis hypothesis) {
            String partialResult = (hypothesis != null && !TextUtils.isEmpty(hypothesis.getHypstr()))
                    ? hypothesis.getHypstr()
                    : "";

            if (mKeywordContainer.get(partialResult) != null) {
                Log.i(TAG, partialResult);
                mSpeechRecognizer.stop();
            }
        }

        /**
         * Called when the {@link SpeechRecognizer} has been stopped. If a keyword is detected,
         * the {@link SpeechRecognizer} starts listening for the context of the keyword. If no
         * keyword/speech is recognized, feedback is given to the user.
         *
         * @param hypothesis Contains the result of the {@link SpeechRecognizer}
         */
        @Override
        public void onResult(Hypothesis hypothesis) {
            Log.i(TAG, "SpeechToTextController.onResult()");
            mIsActive = false;

            if (hypothesis == null) {
                TextToSpeechController.getInstance().speak(
                        mContext.getString(R.string.speech_recognition_feedback_no_result)
                );

                return;
            }

            if (SpeechRecognitionTypesEnum.ALL_KEYWORDS.equals(mSpeechRecognizer.getSearchName())) {
                if (mKeywordContainer.get(hypothesis.getHypstr()) != null) {
                    mSpeechRecognizer.startListening(mKeywordContainer.get(hypothesis.getHypstr()), Constants.SPEECH_TO_TEXT_TIMEOUT);
                    mIsActive = true;
                } else {
                    TextToSpeechController.getInstance().speak(
                            mContext.getString(R.string.speech_recognition_feedback_no_result)
                    );
                }
            } else {
                Log.i(TAG, hypothesis.getHypstr());
                TextToSpeechController.getInstance().speak(hypothesis.getHypstr());
            }
        }

        /**
         * Called when an error has occurred with the {@link SpeechRecognizer}.
         *
         * @param e {@link Exception} which caused the {@link SpeechRecognizer} to stop
         */
        @Override
        public void onError(Exception e) {
            mIsActive = false;
            TextToSpeechController.getInstance().speak(
                    mContext.getString(R.string.speech_recognition_feedback_error)
            );
            Log.e(TAG, "SpeechToTextController.onError()", e);
        }

        /**
         * Called after {@link Constants#SPEECH_TO_TEXT_TIMEOUT} has elapsed and the user
         * has not said anything. The {@link SpeechRecognizer} is stopped and
         * {@link #onResult(Hypothesis)} is being called.
         */
        @Override
        public void onTimeout() {
            Log.i(TAG, "SpeechToTextController.onTimeout()");
            mSpeechRecognizer.stop();
        }
    }
}
