package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URL;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaCollection {
    private final TmdbId id;
    private final String name;
    private final URL posterPath;

    public MediaCollection(TmdbId id, String name, URL posterPath) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
    }

    public TmdbId getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public URL getPosterPath() {
        return this.posterPath;
    }

    @Override
    public String toString() {
        return "MediaCollection{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        MediaCollection that = (MediaCollection) o;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
