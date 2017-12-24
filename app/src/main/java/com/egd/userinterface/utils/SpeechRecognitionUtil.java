package com.egd.userinterface.utils;

import android.content.Context;

import com.egd.userinterface.R;
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Helper class used to manage the resources needed by the
 * {@link com.egd.userinterface.controllers.SpeechToTextController}.
 *
 * @author Petar Krastev
 * @since 23.11.2017
 */
public class SpeechRecognitionUtil {

    /**
     * Provides the correct {@link File} resource for the {@link SpeechRecognitionTypesEnum}
     * used by the {@link com.egd.userinterface.controllers.SpeechToTextController}. The
     * {@link File} resource returned is adjust to the {@link Locale}.
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param type      The type of speech recognition, must be one of {@link SpeechRecognitionTypesEnum}
     * @param locale    The current {@link Locale} of the system
     * @return The {@link File} resource that must be used for the speech recognition type
     */
    public static File getAssetsForKeyword(File assetsDir, @SpeechRecognitionTypesEnum String type, Locale locale) {
        switch (type) {
            case SpeechRecognitionTypesEnum.FIND_KEYWORD:
                if (Locale.US.equals(locale)) {
                    return new File(assetsDir, "find-keyword.gram");
                }
            case SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD:
                if (Locale.US.equals(locale)) {
                    return new File(assetsDir, "navigate-keyword.gram");
                }
            case SpeechRecognitionTypesEnum.TEST_KEYWORD:
                if (Locale.US.equals(locale)) {
                    return new File(assetsDir, "test-keyword.bin");
                }
            case SpeechRecognitionTypesEnum.ALL_KEYWORDS:
            default:
                if (Locale.US.equals(locale)) {
                    return new File(assetsDir, "all-keywords.list");
                } else {
                    throw new RuntimeException(String.format(
                            "SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!",
                            locale
                    ));
                }
        }
    }

    /**
     * Provides the correct acoustic model for {@link com.egd.userinterface.controllers.SpeechToTextController}.
     * The {@link File} resource returned is adjust to the {@link Locale}.
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param locale    The current {@link Locale} of the system
     * @return The {@link File} resource that must be used as an acoustic model for {@link com.egd.userinterface.controllers.SpeechToTextController}
     */
    public static File getAcousticModel(File assetsDir, Locale locale) {
        if (Locale.US.equals(locale)) {
            return new File(assetsDir, "en-us-ptm");
        } else {
            throw new RuntimeException(String.format(
                    "SpeechRecognitionUtil.getAcousticModel() failed! The selected locale:%s is not supported!",
                    locale
            ));
        }
    }

    /**
     * Provides the correct dictionary for {@link com.egd.userinterface.controllers.SpeechToTextController}.
     * The {@link File} resource returned is adjust to the {@link Locale}.
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param locale    The current {@link Locale} of the system
     * @return The {@link File} resource that must be used as a dictionary for {@link com.egd.userinterface.controllers.SpeechToTextController}
     */
    public static File getDictionary(File assetsDir, Locale locale) {
        if (Locale.US.equals(locale)) {
            return new File(assetsDir, "cmudict-en-us.dict");
        } else {
            throw new RuntimeException(String.format(
                    "SpeechRecognitionUtil.getDictionary() failed! The selected locale:%s is not supported!",
                    locale
            ));
        }
    }

    /**
     * Generates a map where the key is the translation of the given {@link SpeechRecognitionTypesEnum}
     * type and the value is the {@link SpeechRecognitionTypesEnum} type itself. This functionality is useful
     * for additional language options.
     *
     * @param context {@link Context} reference
     * @return A map where the key is the translation of the {@link SpeechRecognitionTypesEnum} type, and the value is the {@link SpeechRecognitionTypesEnum} type itself
     */
    public static Map<String, String> mapWordsToSpeechRecognitionTypes(Context context) {
        Map<String, String> result = new HashMap<>();

        result.put(
                context.getResources().getString(R.string.speech_recognition_keyword_find),
                SpeechRecognitionTypesEnum.FIND_KEYWORD
        );

        result.put(
                context.getResources().getString(R.string.speech_recognition_keyword_navigate),
                SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD
        );

        result.put(
                context.getResources().getString(R.string.speech_recognition_keyword_test),
                SpeechRecognitionTypesEnum.TEST_KEYWORD
        );

        return result;
    }
}
