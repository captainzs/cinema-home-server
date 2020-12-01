package com.github.cinema.home.client.server.core.fanart.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FanartDiscImage extends FanartImage {
    private final String disc;
    private final String discType;

    @JsonCreator
    private FanartDiscImage(
            @JsonProperty("id") String id,
            @JsonProperty("url") String url,
            @JsonProperty("lang") String language,
            @JsonProperty("likes") String likes,
            @JsonProperty("disc") String disc,
            @JsonProperty("disc_type") String discType) {
        super(id, url, language, likes);
        this.disc = disc;
        this.discType = discType;
    }

    public String getDisc() {
        return this.disc;
    }

    public String getDiscType() {
        return this.discType;
    }

    @Override
    public String toString() {
        return "FanartDiscImage{" +
                "id='" + this.id + '\'' +
                ", url='" + this.url + '\'' +
                ", iso639Id='" + this.iso639Id + '\'' +
                ", likes='" + this.likes + '\'' +
                ", disc='" + this.disc + '\'' +
                ", discType='" + this.discType + '\'' +
                '}';
    }
}
