package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public class TmdbImagesResult {
    private final List<TmdbImage> backdrops;
    private final List<TmdbImage> posters;

    @JsonCreator
    public TmdbImagesResult(
            @JsonProperty("backdrops") List<TmdbImage> backdrops,
            @JsonProperty("posters") List<TmdbImage> posters) {
        this.backdrops = backdrops;
        this.posters = posters;
    }

    public List<TmdbImage> getBackdrops() {
        return this.backdrops;
    }

    public List<TmdbImage> getPosters() {
        return this.posters;
    }

    public LinkedHashSet<Image> toBackdropImages() {
        LinkedHashSet<Image> images = new LinkedHashSet<>(this.backdrops.size());
        for (TmdbImage tImage : this.backdrops) {
            URL url = WebUrlMaker.getTmdbImageUrl(tImage.getFilePath(), "original").orElse(null);
            boolean languageSpecific = !StringUtils.isEmpty(tImage.getIso639Id()) && !tImage.getIso639Id().equals("null");
            images.add(languageSpecific ? Image.locale(url, Locale.forLanguageTag(tImage.getIso639Id())) : Image.clear(url));
        }
        return images;
    }

    public LinkedHashSet<Image> toPosterImages() {
        LinkedHashSet<Image> images = new LinkedHashSet<>(this.posters.size());
        for (TmdbImage tImage : this.posters) {
            URL url = WebUrlMaker.getTmdbImageUrl(tImage.getFilePath(), "original").orElse(null);
            boolean languageSpecific = !StringUtils.isEmpty(tImage.getIso639Id()) && !tImage.getIso639Id().equals("null");
            images.add(languageSpecific ? Image.locale(url, Locale.forLanguageTag(tImage.getIso639Id())) : Image.clear(url));
        }
        return images;
    }
}
