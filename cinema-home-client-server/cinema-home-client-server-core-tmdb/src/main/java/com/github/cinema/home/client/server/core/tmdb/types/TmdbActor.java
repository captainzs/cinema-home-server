package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.Actor;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;

import java.net.URL;

public class TmdbActor {
    private final String name;
    private final String character;
    private final String profilePath;

    @JsonCreator
    private TmdbActor(
            @JsonProperty("name") String name,
            @JsonProperty("character") String character,
            @JsonProperty("profile_path") String profilePath) {
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
    }

    public String getName() {
        return this.name;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getProfilePath() {
        return this.profilePath;
    }

    public Actor toActor() {
        URL profileUrl = WebUrlMaker.getTmdbImageUrl(this.profilePath, "w185").orElse(null);
        return new Actor(this.name, this.character, profileUrl);
    }

    @Override
    public String toString() {
        return "TmdbActor{" +
                "name='" + this.name + '\'' +
                ", character='" + this.character + '\'' +
                ", profilePath='" + this.profilePath + '\'' +
                '}';
    }
}
