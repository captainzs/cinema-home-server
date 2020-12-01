package com.github.cinema.home.client.server.common.types.tech;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Language {
    HUNGARIAN("HU", "HUN", "Hungarian"),
    ENGLISH("ENG", "GBR", "English"),
    ITALIAN("ITA", "Italian"),
    SPANISH("SPA", "Spanish"),
    GERMAN("GER", "German"),
    RUSSIAN("RUS", "Russian"),
    DANISH("DK"),
    PORTUGAL("POR"),
    FRENCH("FRENCH", "FR", "FRA"),
    HINDI("Hindi");

    private static final Logger logger = LoggerFactory.getLogger(ColorCodec.class);

    private final String[] codes;

    Language(String... codes) {
        this.codes = codes;
    }

    public static Optional<Language> of(String code) {
        for (Language lang : Language.values()) {
            if (Arrays.stream(lang.codes).anyMatch(c -> c.toLowerCase().equals(code.toLowerCase()))) {
                return Optional.of(lang);
            }
        }
        logger.warn(String.format("Language is not defined for: %s!", code));
        return Optional.empty();
    }

    public static List<String> allSynonyms() {
        return Arrays.stream(Language.values())
                .map(l -> l.codes)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
