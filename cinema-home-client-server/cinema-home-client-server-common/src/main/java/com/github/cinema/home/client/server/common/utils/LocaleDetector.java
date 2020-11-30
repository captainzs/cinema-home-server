package com.github.cinema.home.client.server.common.utils;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

import java.util.Locale;

public class LocaleDetector {
    public Locale detect(String text) {
        LanguageDetector languageDetector = new OptimaizeLangDetector().loadModels();
        LanguageResult result = languageDetector.detect(text);
        return result.isReasonablyCertain() ? Locale.forLanguageTag(result.getLanguage()) : null;
    }
}
