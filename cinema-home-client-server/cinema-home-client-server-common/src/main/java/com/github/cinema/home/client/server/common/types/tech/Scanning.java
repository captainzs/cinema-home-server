package com.github.cinema.home.client.server.common.types.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum Scanning {
    INTERLACED("i"),
    PROGRESSIVE("p");

    private static final Logger logger = LoggerFactory.getLogger(Scanning.class);

    private final String abbreviation;

    Scanning(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static Optional<Scanning> of(String abbreviation) {
        for (Scanning scan : Scanning.values()) {
            if (scan.abbreviation.equals(abbreviation)) {
                return Optional.of(scan);
            }
        }
        logger.warn(String.format("Scanning is not defined for '%s' abbreviation!", abbreviation));
        return Optional.empty();
    }
}
