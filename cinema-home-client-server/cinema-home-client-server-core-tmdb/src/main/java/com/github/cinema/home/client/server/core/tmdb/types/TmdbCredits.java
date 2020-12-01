package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbCredits {
    private final List<TmdbActor> actors;
    private final List<TmdbCreator> crew;

    @JsonCreator
    private TmdbCredits(
            @JsonProperty("cast") List<TmdbActor> actors,
            @JsonProperty("crew") List<TmdbCreator> crew) {
        this.actors = actors;
        this.crew = crew;
    }

    public List<TmdbActor> getActors() {
        return this.actors;
    }

    public List<TmdbCreator> getCrew() {
        return this.crew;
    }

    @Override
    public String toString() {
        return "TmdbCredits{" +
                "actors=" + this.actors +
                ", crew=" + this.crew +
                '}';
    }
}
