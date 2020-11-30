package com.github.cinema.home.client.server.common.types.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum Resolution {
    x480(ResolutionDefinition.SD),
    x576(ResolutionDefinition.SD),
    x720(ResolutionDefinition.HD),
    x1080(ResolutionDefinition.HD),
    x1440(ResolutionDefinition.HD),
    x2160(ResolutionDefinition.UHD),
    x2880(ResolutionDefinition.UHD),
    x4320(ResolutionDefinition.UHD);

    private static final Logger logger = LoggerFactory.getLogger(Resolution.class);

    public final ResolutionDefinition byDefinition;

    Resolution(ResolutionDefinition byDefinition) {
        this.byDefinition = byDefinition;
    }

    public static Optional<Resolution> of(String verticalPixels) {
        for (Resolution value : Resolution.values()) {
            if (value.name().equals("x" + verticalPixels)) {
                return Optional.of(value);
            }
        }
        logger.warn(String.format("Resolution is not defined for %s vertical pixels!", verticalPixels));
        return Optional.empty();
    }
}
