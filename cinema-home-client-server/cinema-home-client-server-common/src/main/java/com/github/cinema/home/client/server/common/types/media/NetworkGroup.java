package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO: AMC, FOX, Cartoon Network, Comedy Central, Showtime
public enum NetworkGroup {
    ABC("ABC"),
    APPLE("ATVP"),
    AMAZON("AMZN"),
    BBC("BBC"),
    CBS("CBS"),
    CRITERION("CC"),
    CW("CW"),
    DCU("DCU"),
    DISNEY("DSNP", "DSNY"),
    HBO("HMAX", "HBO"),
    HULU("HULU"),
    ITUNES("iTunes"),
    MTV("MTV"),
    MTVA("MTVA"),
    NBC("NBC"),
    NICKELODEON("NICK"),
    NETFLIX("NF", "Netflix"),
    STARZ("STZ");

    private static final Logger logger = LoggerFactory.getLogger(NetworkGroup.class);

    private final String[] synonyms;

    NetworkGroup(String... synonyms) {
        this.synonyms = synonyms;
    }

    public String getSearchLabel() {
        return "." + this.synonyms[0] + ".";
    }

    public static Optional<NetworkGroup> of(int ordinal) {
        for (NetworkGroup network : NetworkGroup.values()) {
            if (network.ordinal() == ordinal) {
                return Optional.of(network);
            }
        }
        logger.warn(String.format("Network is not defined for id: %d!", ordinal));
        return Optional.empty();
    }

    public static Optional<NetworkGroup> of(String networkName) {
        for (NetworkGroup network : NetworkGroup.values()) {
            if (Arrays.stream(network.synonyms).anyMatch(c -> c.toLowerCase().equals(networkName.toLowerCase()))) {
                return Optional.of(network);
            }
        }
        logger.warn(String.format("Network is not defined for %s!", networkName));
        return Optional.empty();
    }

    public static List<String> allSynonyms() {
        return Arrays.stream(NetworkGroup.values())
                .map(c -> c.synonyms)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
