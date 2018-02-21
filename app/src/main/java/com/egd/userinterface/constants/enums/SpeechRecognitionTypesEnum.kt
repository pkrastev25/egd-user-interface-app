package com.egd.userinterface.constants.enums

import android.support.annotation.StringDef

/**
 * Specifies all the speech recognition capabilities of the
 * [com.egd.userinterface.controllers.SpeechToTextController].
 *
 * @author Petar Krastev
 * @since 23.11.2017
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        SpeechRecognitionTypesEnum.ALL_KEYWORDS,
        SpeechRecognitionTypesEnum.FIND_KEYWORD,
        SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD,
        SpeechRecognitionTypesEnum.TEST_KEYWORD
)
annotation class SpeechRecognitionTypesEnumAnnotation

object SpeechRecognitionTypesEnum {

    /**
     * Specifies that the [com.egd.userinterface.controllers.SpeechToTextController]
     * should recognize keywords stored in all-keywords.list.
     */
    const val ALL_KEYWORDS = "ALL_KEYWORDS"

    /**
     * Specifies that the [com.egd.userinterface.controllers.SpeechToTextController]
     * should recognize keywords stored in find-keyword.gram.
     */
    const val FIND_KEYWORD = "FIND_KEYWORD"

    /**
     * Specifies that the [com.egd.userinterface.controllers.SpeechToTextController]
     * should recognize keywords stored in navigate-keyword.gram.
     */
    const val NAVIGATE_KEYWORD = "NAVIGATE_KEYWORD"

    /**
     * Specifies that the [com.egd.userinterface.controllers.SpeechToTextController]
     * should recognize natural language processing according to test-keyword.bin.
     */
    const val TEST_KEYWORD = "TEST_KEYWORD"
}
