package com.github.cinema.home.client.server.common.types.tech;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ColorCodec {
    PAL,
    NTSC,
    SECAM;

    private static final Logger logger = LoggerFactory.getLogger(ColorCodec.class);

    public static Optional<ColorCodec> of(String codecName) {
        for (ColorCodec codec : ColorCodec.values()) {
            if (codec.name().toLowerCase().equals(codecName.toLowerCase())) {
                return Optional.of(codec);
            }
        }
        logger.warn(String.format("Color-codec is not defined for %s!", codecName));
        return Optional.empty();
    }

    public static List<String> allSynonyms() {
        List<String> synonyms = new ArrayList<>();
        for (ColorCodec codec : ColorCodec.values()) {
            synonyms.add(codec.name());
        }
        return synonyms;
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
