package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbFindResult {
    private final List<TmdbMovie> movieResults;
    private final List<TmdbShow> showResults;

    @JsonCreator
    private TmdbFindResult(
            @JsonProperty("movie_results") List<TmdbMovie> movieResults,
            @JsonProperty("tv_results") List<TmdbShow> showResults) {
        this.movieResults = movieResults;
        this.showResults = showResults;
    }

    public List<TmdbMovie> getMovieResults() {
        return this.movieResults;
    }

    public List<TmdbShow> getShowResults() {
        return this.showResults;
    }

    @Override
    public String toString() {
        return "TmdbFindResult{" +
                "movieResults=" + this.movieResults +
                ", showResults=" + this.showResults +
                '}';
    }
}
