package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbImage {
    private final String filePath;
    private final int width;
    private final int height;
    private final double aspectRatio;
    private final String iso639Id;
    private final double voteAverage;
    private final int voteCount;

    @JsonCreator
    private TmdbImage(
            @JsonProperty("file_path") String filePath,
            @JsonProperty("width") int width,
            @JsonProperty("height") int height,
            @JsonProperty("aspect_ratio") double aspectRatio,
            @JsonProperty("iso_639_1") String iso639Id,
            @JsonProperty("vote_average") double voteAverage,
            @JsonProperty("vote_count") int voteCount) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.aspectRatio = aspectRatio;
        this.iso639Id = iso639Id;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public double getAspectRatio() {
        return this.aspectRatio;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public int getVoteCount() {
        return this.voteCount;
    }
}
