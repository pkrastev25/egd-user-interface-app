package com.egd.userinterface.constants.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies all the speech recognition capabilities of the
 * {@link com.egd.userinterface.controllers.SpeechToTextController}.
 *
 * @author Petar Krastev
 * @since 23.11.2017
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        SpeechRecognitionTypes.ALL_KEYWORDS,
        SpeechRecognitionTypes.FIND_KEYWORD,
        SpeechRecognitionTypes.NAVIGATE_KEYWORD,
        SpeechRecognitionTypes.TEST_KEYWORD
})
public @interface SpeechRecognitionTypes {

    /**
     * Specifies that the {@link com.egd.userinterface.controllers.SpeechToTextController}
     * should recognize keywords stored in all-keywords.gram.
     */
    String ALL_KEYWORDS = "ALL_KEYWORDS";

    /**
     * Specifies that the {@link com.egd.userinterface.controllers.SpeechToTextController}
     * should recognize keywords stored in find-keyword.gram.
     */
    String FIND_KEYWORD = "FIND_KEYWORD";

    /**
     * Specifies that the {@link com.egd.userinterface.controllers.SpeechToTextController}
     * should recognize keywords stored in navigate-keyword.gram.
     */
    String NAVIGATE_KEYWORD = "NAVIGATE_KEYWORD";

    /**
     * Specifies that the {@link com.egd.userinterface.controllers.SpeechToTextController}
     * should recognize keywords stored in test-keyword.gram.
     */
    String TEST_KEYWORD = "TEST_KEYWORD";
}
