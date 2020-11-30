package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TmdbGenre {
    private final int id;
    private final String name;

    @JsonCreator
    private TmdbGenre(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public static TmdbGenre of(int id, String name) {
        return new TmdbGenre(id, name);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        TmdbGenre tmdbGenre = (TmdbGenre) o;
        return this.id == tmdbGenre.id &&
                Objects.equals(this.name, tmdbGenre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "TmdbGenre{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }
}
