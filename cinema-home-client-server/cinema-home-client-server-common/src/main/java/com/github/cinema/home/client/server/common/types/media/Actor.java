package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URL;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Actor {
    private final String name;
    private final String character;
    private final URL profilePath;

    public Actor(String name, String character, URL profilePath) {
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

    public URL getProfilePath() {
        return this.profilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return this.name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
