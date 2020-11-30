package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class TmdbId {
    private final int id;

    public static TmdbId of(int id) {
        return new TmdbId(id);
    }

    private TmdbId(int id) {
        this.id = id;
    }

    @JsonValue
    public int intValue() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        TmdbId tmdbId = (TmdbId) o;
        return this.id == tmdbId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
