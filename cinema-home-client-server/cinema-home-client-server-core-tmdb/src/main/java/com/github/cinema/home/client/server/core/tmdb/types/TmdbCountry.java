package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbCountry {
    private final String isoId;
    private final String name;

    @JsonCreator
    private TmdbCountry(
            @JsonProperty("iso_3166_1") String isoId,
            @JsonProperty("name") String name) {
        this.isoId = isoId;
        this.name = name;
    }

    public String getIsoId() {
        return this.isoId;
    }

    public String getName() {
        return this.name;
    }
}
