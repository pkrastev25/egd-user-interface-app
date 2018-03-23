package com.egd.userinterface.utils

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.constants.annotations.SpeechRecognitionTypesAnnotation
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum
import java.io.File
import java.util.*

/**
 * @author Petar Krastev
 */
object SpeechRecognitionUtil {

    fun getAssetsForKeywordAndLocale(
            assetsDir: File,
            @SpeechRecognitionTypesAnnotation
            type: String,
            locale: Locale
    ): File {
        return when (type) {
            SpeechRecognitionTypesEnum.FIND_KEYWORD -> {
                when (locale) {
                    Locale.US -> {
                        File(assetsDir, "find-keyword.gram")
                    }
                    else -> {
                        throw RuntimeException("The specified locale: $locale is unknown!")
                    }
                }
            }
            SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD -> {
                when (locale) {
                    Locale.US -> {
                        File(assetsDir, "navigate-keyword.gram")
                    }
                    else -> {
                        throw RuntimeException("The specified locale: $locale is unknown!")
                    }
                }
            }
            SpeechRecognitionTypesEnum.TEST_KEYWORD -> {
                when (locale) {
                    Locale.US -> {
                        File(assetsDir, "test-keyword.bin")
                    }
                    else -> {
                        throw RuntimeException("The specified locale: $locale is unknown!")
                    }
                }
            }
            SpeechRecognitionTypesEnum.ALL_KEYWORDS -> {
                when (locale) {
                    Locale.US -> {
                        File(assetsDir, "all-keywords.list")
                    }
                    else -> {
                        throw RuntimeException("The specified locale: $locale is unknown!")
                    }
                }
            }
            else -> {
                throw RuntimeException("The specified speech recognition type: $type is unknown!")
            }
        }
    }

    fun getAcousticModelForLocale(assetsDir: File, locale: Locale): File {
        return when (locale) {
            Locale.US -> {
                File(assetsDir, "en-us-ptm")
            }
            else -> {
                throw RuntimeException("The specified locale: $locale is unknown!")
            }
        }
    }

    fun getDictionaryForLocale(assetsDir: File, locale: Locale): File {
        return when(locale) {
            Locale.US -> {
                File(assetsDir, "cmudict-en-us.dict")
            }
            else -> {
                throw RuntimeException("The specified locale: $locale is unknown!")
            }
        }
    }

    fun getKeywordTranslationToKeywordDefinitionMap(context: Context): Map<String, String> {
        val result = HashMap<String, String>()

        result[context.resources.getString(R.string.speech_recognition_keyword_find)] = SpeechRecognitionTypesEnum.FIND_KEYWORD
        result[context.resources.getString(R.string.speech_recognition_keyword_navigate)] = SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD
        result[context.resources.getString(R.string.speech_recognition_keyword_test)] = SpeechRecognitionTypesEnum.TEST_KEYWORD

        return result
    }
}
