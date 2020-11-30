package com.github.cinema.home.client.server.common.types.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Container {
    TS("TS"),
    AVI("AVI"),
    MP4("MP4"),
    MKV("MKV");

    private static final Logger logger = LoggerFactory.getLogger(Container.class);

    private final String[] synonyms;

    Container(String... synonyms) {
        this.synonyms = synonyms;
    }

    public static Optional<Container> of(String containerName) {
        for (Container container : Container.values()) {
            if (Arrays.stream(container.synonyms).anyMatch(c -> c.toLowerCase().equals(containerName.toLowerCase()))) {
                return Optional.of(container);
            }
        }
        logger.warn(String.format("Container is not defined for %s!", containerName));
        return Optional.empty();
    }

    public static List<String> allSynonyms() {
        return Arrays.stream(Container.values())
                .map(c -> c.synonyms)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
