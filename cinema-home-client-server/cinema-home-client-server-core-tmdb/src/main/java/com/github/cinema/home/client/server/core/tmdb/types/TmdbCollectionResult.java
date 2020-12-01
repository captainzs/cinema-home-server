package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbCollectionResult<T> {
    private final int id;
    private final String name;
    private final String overview;
    private final String posterPath;
    private final String backdropPath;
    private final List<T> parts;

    @JsonCreator
    private TmdbCollectionResult(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("overview") String overview,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("backdrop_path") String backdropPath,
            @JsonProperty("parts") List<T> parts) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.parts = parts;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public List<T> getParts() {
        return this.parts;
    }

    @Override
    public String toString() {
        return "TmdbCollectionResult{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", overview='" + this.overview + '\'' +
                ", posterPath='" + this.posterPath + '\'' +
                ", backdropPath='" + this.backdropPath + '\'' +
                ", parts=" + this.parts +
                '}';
    }
}
