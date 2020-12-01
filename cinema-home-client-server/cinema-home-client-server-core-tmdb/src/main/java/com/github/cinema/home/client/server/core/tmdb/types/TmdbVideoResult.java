package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbVideoResult {
    private final List<TmdbVideo> results;

    @JsonCreator
    private TmdbVideoResult(
            @JsonProperty("results") List<TmdbVideo> results) {
        this.results = results;
    }

    public List<TmdbVideo> getResults() {
        return this.results;
    }
}
