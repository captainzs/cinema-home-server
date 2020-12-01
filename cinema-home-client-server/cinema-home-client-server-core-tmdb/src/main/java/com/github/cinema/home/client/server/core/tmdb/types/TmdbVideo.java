package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbVideo {
    private final String id;
    private final String iso639Id;
    private final String iso3166Id;
    private final String key;
    private final String name;
    private final String site;
    private final Integer size;
    private final String type;

    @JsonCreator
    private TmdbVideo(
            @JsonProperty("id") String id,
            @JsonProperty("iso_639_1") String iso639Id,
            @JsonProperty("iso_3166_1") String iso3166Id,
            @JsonProperty("key") String key,
            @JsonProperty("name") String name,
            @JsonProperty("site") String site,
            @JsonProperty("size") Integer size,
            @JsonProperty("type") String type) {
        this.id = id;
        this.iso639Id = iso639Id;
        this.iso3166Id = iso3166Id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    public String getIso3166Id() {
        return this.iso3166Id;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getSite() {
        return this.site;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getType() {
        return this.type;
    }
}
