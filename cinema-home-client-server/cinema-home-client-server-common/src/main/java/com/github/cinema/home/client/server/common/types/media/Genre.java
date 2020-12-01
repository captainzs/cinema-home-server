package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum Genre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    EDUCATIONAL,
    FAMILY,
    FANTASY,
    HISTORY,
    HORROR,
    MUSIC,
    MYSTERY,
    REALITY,
    ROMANCE,
    SCI_FI,
    SHORT,
    SPORT,
    TALK_SHOW,
    THRILLER,
    WAR,
    WESTERN;

    private static final Logger logger = LoggerFactory.getLogger(Genre.class);

    @JsonCreator
    public static Optional<Genre> of(int ordinal) {
        for (Genre genre : Genre.values()) {
            if (genre.ordinal() == ordinal) {
                return Optional.of(genre);
            }
        }
        logger.warn(String.format("Genre is not defined for id: %d!", ordinal));
        return Optional.empty();
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
