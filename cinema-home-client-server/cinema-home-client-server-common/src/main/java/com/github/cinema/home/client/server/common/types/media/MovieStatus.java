package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum MovieStatus {
    Planned,
    Released,
    InProduction,
    PostProduction;

    private static final Logger logger = LoggerFactory.getLogger(ShowStatus.class);

    public static Optional<MovieStatus> of(int ordinal) {
        for (MovieStatus status : MovieStatus.values()) {
            if (status.ordinal() == ordinal) {
                return Optional.of(status);
            }
        }
        logger.warn(String.format("Movie-status is not defined for id: %d!", ordinal));
        return Optional.empty();
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
