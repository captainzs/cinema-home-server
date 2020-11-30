package com.github.cinema.home.client.server.core.fanart.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FanartImage {
    protected final String id;
    protected final String url;
    protected final String iso639Id;
    protected final String likes;

    @JsonCreator
    protected FanartImage(
            @JsonProperty("id") String id,
            @JsonProperty("url") String url,
            @JsonProperty("lang") String iso639Id,
            @JsonProperty("likes") String likes) {
        this.id = id;
        this.url = url;
        this.iso639Id = iso639Id;
        this.likes = likes;
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    public String getLikes() {
        return this.likes;
    }

    @Override
    public String toString() {
        return "FanartImage{" +
                "id='" + this.id + '\'' +
                ", url='" + this.url + '\'' +
                ", iso639Id='" + this.iso639Id + '\'' +
                ", likes='" + this.likes + '\'' +
                '}';
    }
}
