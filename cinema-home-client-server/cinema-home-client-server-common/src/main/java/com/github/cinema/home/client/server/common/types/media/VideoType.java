package com.github.cinema.home.client.server.common.types.media;

import java.util.Arrays;

public enum VideoType {
    Trailer,
    Teaser,
    Clip,
    Featurette,
    OpeningCredits("Opening Credits"),
    BehindTheScenes("Behind The Scenes"),
    Bloopers;

    private final String[] values;

    VideoType(String... values) {
        this.values = values;
    }

    public static VideoType of(String str) {
        for (VideoType videoType : VideoType.values()) {
            if (videoType.name().toLowerCase().equals(str.toLowerCase()) ||
                    Arrays.stream(videoType.values).anyMatch(v -> v.toLowerCase().equals(str.toLowerCase()))) {
                return videoType;
            }
        }
        return null;
    }
}
