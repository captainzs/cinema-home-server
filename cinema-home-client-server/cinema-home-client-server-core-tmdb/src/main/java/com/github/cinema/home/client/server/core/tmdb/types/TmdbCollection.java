package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.MediaCollection;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;

public class TmdbCollection {
    private final int id;
    private final String name;
    private final String posterPath;
    private final String backdropPath;

    @JsonCreator
    private TmdbCollection(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("backdrop_path") String backdropPath) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public MediaCollection toCollection() {
        return new MediaCollection(TmdbId.of(this.id), this.getName(), WebUrlMaker.getTmdbImageUrl(this.posterPath, "original").orElse(null));
    }
}
