package com.egd.userinterface.utils

import android.content.Context

import com.egd.userinterface.R
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnumAnnotation

import java.io.File
import java.util.HashMap
import java.util.Locale

/**
 * Helper class used to manage the resources needed by the
 * [com.egd.userinterface.controllers.SpeechToTextController].
 *
 * @author Petar Krastev
 * @since 23.11.2017
 */
object SpeechRecognitionUtil {

    /**
     * Provides the correct [File] resource for the [SpeechRecognitionTypesEnum]
     * used by the [com.egd.userinterface.controllers.SpeechToTextController]. The
     * [File] resource returned is adjusted according to the [Locale].
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param type      The type of speech recognition, must be one of [SpeechRecognitionTypesEnum]
     * @param locale    The current [Locale] of the system
     * @return The [File] resource that must be used for the speech recognition type
     */
    fun getAssetsForKeyword(assetsDir: File, @SpeechRecognitionTypesEnumAnnotation type: String, locale: Locale): File {
        when (type) {
            SpeechRecognitionTypesEnum.FIND_KEYWORD -> {
                if (Locale.US == locale) {
                    return File(assetsDir, "find-keyword.gram")
                }
                if (Locale.US == locale) {
                    return File(assetsDir, "navigate-keyword.gram")
                }
                if (Locale.US == locale) {
                    return File(assetsDir, "test-keyword.bin")
                }
                return if (Locale.US == locale) {
                    File(assetsDir, "all-keywords.list")
                } else {
                    throw RuntimeException(String.format(
                            "SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!",
                            locale
                    ))
                }
            }
            SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD -> {
                if (Locale.US == locale) {
                    return File(assetsDir, "navigate-keyword.gram")
                }
                if (Locale.US == locale) {
                    return File(assetsDir, "test-keyword.bin")
                }
                return if (Locale.US == locale) {
                    File(assetsDir, "all-keywords.list")
                } else {
                    throw RuntimeException(String.format("SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!", locale))
                }
            }
            SpeechRecognitionTypesEnum.TEST_KEYWORD -> {
                if (Locale.US == locale) {
                    return File(assetsDir, "test-keyword.bin")
                }
                return if (Locale.US == locale) {
                    File(assetsDir, "all-keywords.list")
                } else {
                    throw RuntimeException(String.format("SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!", locale))
                }
            }
            SpeechRecognitionTypesEnum.ALL_KEYWORDS -> return if (Locale.US == locale) {
                File(assetsDir, "all-keywords.list")
            } else {
                throw RuntimeException(String.format("SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!", locale))
            }
            else -> return if (Locale.US == locale) {
                File(assetsDir, "all-keywords.list")
            } else {
                throw RuntimeException(String.format("SpeechRecognitionUtil.getAssetsForKeyword() failed! The selected locale:%s is not supported!", locale))
            }
        }
    }

    /**
     * Provides the correct acoustic model for [com.egd.userinterface.controllers.SpeechToTextController].
     * The [File] resource returned is adjusted according to the [Locale].
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param locale    The current [Locale] of the system
     * @return The [File] resource that must be used as an acoustic model for [com.egd.userinterface.controllers.SpeechToTextController]
     */
    fun getAcousticModel(assetsDir: File, locale: Locale): File {
        return if (Locale.US == locale) {
            File(assetsDir, "en-us-ptm")
        } else {
            throw RuntimeException(String.format(
                    "SpeechRecognitionUtil.getAcousticModel() failed! The selected locale:%s is not supported!",
                    locale
            ))
        }
    }

    /**
     * Provides the correct dictionary for [com.egd.userinterface.controllers.SpeechToTextController].
     * The [File] resource returned is adjusted according to the [Locale].
     *
     * @param assetsDir The directory where all the asset files are stored
     * @param locale    The current [Locale] of the system
     * @return The [File] resource that must be used as a dictionary for [com.egd.userinterface.controllers.SpeechToTextController]
     */
    fun getDictionary(assetsDir: File, locale: Locale): File {
        return if (Locale.US == locale) {
            File(assetsDir, "cmudict-en-us.dict")
        } else {
            throw RuntimeException(String.format(
                    "SpeechRecognitionUtil.getDictionary() failed! The selected locale:%s is not supported!",
                    locale
            ))
        }
    }

    /**
     * Generates a map where the key is the translation of the given [SpeechRecognitionTypesEnum]
     * type and the value is the [SpeechRecognitionTypesEnum] type itself. This functionality is useful
     * for additional language options.
     * Note, the assumption is made that the correct translation will be used based
     * on the [Locale] of the system!
     *
     * @param context [Context] reference
     * @return A map where the key is the translation of the [SpeechRecognitionTypesEnum] type, and the value is the [SpeechRecognitionTypesEnum] type itself
     */
    fun mapWordsToSpeechRecognitionTypes(context: Context): Map<String, String> {
        val result = HashMap<String, String>()

        result[context.resources.getString(R.string.speech_recognition_keyword_find)] = SpeechRecognitionTypesEnum.FIND_KEYWORD

        result[context.resources.getString(R.string.speech_recognition_keyword_navigate)] = SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD

        result[context.resources.getString(R.string.speech_recognition_keyword_test)] = SpeechRecognitionTypesEnum.TEST_KEYWORD

        return result
    }
}
