package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class ImdbId {
    private final String id;

    public static ImdbId of(@NonNull String id) {
        return new ImdbId(id);
    }

    private ImdbId(String id) {
        this.id = id;
    }

    @JsonValue
    public String strValue() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ImdbId imdbId = (ImdbId) o;
        return this.id.equals(imdbId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
