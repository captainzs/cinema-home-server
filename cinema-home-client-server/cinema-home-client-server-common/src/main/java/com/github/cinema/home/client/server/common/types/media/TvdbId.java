package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class TvdbId {
    private final long id;

    public static TvdbId of(long id) {
        return new TvdbId(id);
    }

    private TvdbId(long id) {
        this.id = id;
    }

    private TvdbId(String id) {
        this.id = Long.parseLong(id);
    }

    @JsonValue
    public long longValue() {
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
        TvdbId tvdbId = (TvdbId) o;
        return this.id == tvdbId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
