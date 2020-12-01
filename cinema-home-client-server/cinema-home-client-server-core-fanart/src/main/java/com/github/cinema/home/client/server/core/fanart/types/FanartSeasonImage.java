package com.github.cinema.home.client.server.core.fanart.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FanartSeasonImage extends FanartImage {
    private final String season;

    @JsonCreator
    private FanartSeasonImage(
            @JsonProperty("id") String id,
            @JsonProperty("url") String url,
            @JsonProperty("lang") String language,
            @JsonProperty("likes") String likes,
            @JsonProperty("season") String season) {
        super(id, url, language, likes);
        this.season = season;
    }

    public String getSeason() {
        return this.season;
    }

    @Override
    public String toString() {
        return "FanartSeasonImage{" +
                "id='" + this.id + '\'' +
                ", url='" + this.url + '\'' +
                ", iso639Id='" + this.iso639Id + '\'' +
                ", likes='" + this.likes + '\'' +
                ", season='" + this.season + '\'' +
                '}';
    }
}
