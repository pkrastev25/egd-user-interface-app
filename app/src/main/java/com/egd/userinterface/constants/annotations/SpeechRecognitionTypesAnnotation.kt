package com.egd.userinterface.constants.annotations

import android.support.annotation.StringDef
import com.egd.userinterface.constants.enums.SpeechRecognitionTypesEnum

/**
 * @author Petar Krastev
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        SpeechRecognitionTypesEnum.ALL_KEYWORDS,
        SpeechRecognitionTypesEnum.FIND_KEYWORD,
        SpeechRecognitionTypesEnum.NAVIGATE_KEYWORD,
        SpeechRecognitionTypesEnum.TEST_KEYWORD
)
annotation class SpeechRecognitionTypesAnnotation