package com.github.cinema.home.client.server.core.fanart.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import com.github.cinema.home.client.server.core.fanart.utils.WebUrlMaker;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// TODO send season images to client as well
public class FanartShow extends FanartMedia {
    private final Long tvdbId;
    private final List<FanartImage> characterArts;
    private final List<FanartSeasonImage> seasonPosters;
    private final List<FanartSeasonImage> seasonThumbs;
    private final List<FanartSeasonImage> seasonBanners;

    @JsonCreator
    private FanartShow(
            @JsonProperty("name") String name,
            @JsonProperty("thetvdb_id") Long tvdbId,
            @JsonProperty("hdclearart") List<FanartImage> hdClearArts,
            @JsonProperty("hdtvlogo") List<FanartImage> hdClearLogos,
            @JsonProperty("tvposter") List<FanartImage> posters,
            @JsonProperty("showbackground") List<FanartImage> backgrounds,
            @JsonProperty("tvthumb") List<FanartImage> thumbs,
            @JsonProperty("clearart") List<FanartImage> clearArts,
            @JsonProperty("tvbanner") List<FanartImage> banners,
            @JsonProperty("clearlogo") List<FanartImage> clearLogos,
            @JsonProperty("characterart") List<FanartImage> characterArts,
            @JsonProperty("seasonposter") List<FanartSeasonImage> seasonPosters,
            @JsonProperty("seasonthumb") List<FanartSeasonImage> seasonThumbs,
            @JsonProperty("seasonbanner") List<FanartSeasonImage> seasonBanners) {
        super(name, hdClearArts, hdClearLogos, posters, backgrounds, thumbs, clearArts, banners, clearLogos);
        this.tvdbId = tvdbId;
        this.characterArts = characterArts;
        this.seasonPosters = seasonPosters;
        this.seasonThumbs = seasonThumbs;
        this.seasonBanners = seasonBanners;
    }

    public TvdbId getTvdbId() {
        return this.tvdbId != null ? TvdbId.of(this.tvdbId) : null;
    }

    public List<FanartImage> getCharacterArts() {
        return this.characterArts;
    }

    public List<FanartSeasonImage> getSeasonPosters() {
        return this.seasonPosters;
    }

    public List<FanartSeasonImage> getSeasonThumbs() {
        return this.seasonThumbs;
    }

    public List<FanartSeasonImage> getSeasonBanners() {
        return this.seasonBanners;
    }

    @Override
    public String toString() {
        return "FanartShow{" +
                "name='" + this.name + '\'' +
                ", tvdbId='" + this.tvdbId + '\'' +
                ", hdClearArts=" + this.hdClearArts +
                ", hdClearLogos=" + this.hdClearLogos +
                ", posters=" + this.posters +
                ", backgrounds=" + this.backgrounds +
                ", thumbs=" + this.thumbs +
                ", clearArts=" + this.clearArts +
                ", banners=" + this.banners +
                ", clearLogos=" + this.clearLogos +
                ", characterArts=" + this.characterArts +
                ", seasonPosters=" + this.seasonPosters +
                ", seasonThumbs=" + this.seasonThumbs +
                ", seasonBanners=" + this.seasonBanners +
                '}';
    }

    @Override
    public ShowNfo toNfo() {
        return ShowNfo.builder()
                .title(LocaleString.locale(this.name, Locale.ENGLISH))
                .tvdbId(this.getTvdbId())
                .backdrops(this.backgrounds == null ? null : this.backgrounds.stream()
                        .map(i -> Image.locale(WebUrlMaker.getFanartImageUrl(i.getUrl()).orElse(null), Locale.forLanguageTag(i.getIso639Id())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .logos(this.hdClearLogos == null ? null : this.hdClearLogos.stream()
                        .map(i -> Image.locale(WebUrlMaker.getFanartImageUrl(i.getUrl()).orElse(null), Locale.forLanguageTag(i.getIso639Id())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .logos(this.clearLogos == null ? null : this.clearLogos.stream()
                        .map(i -> Image.locale(WebUrlMaker.getFanartImageUrl(i.getUrl()).orElse(null), Locale.forLanguageTag(i.getIso639Id())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .posters(this.posters == null ? null : this.posters.stream()
                        .map(i -> Image.locale(WebUrlMaker.getFanartImageUrl(i.getUrl()).orElse(null), Locale.forLanguageTag(i.getIso639Id())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .thumbs(this.thumbs == null ? null : this.thumbs.stream()
                        .map(i -> Image.locale(WebUrlMaker.getFanartImageUrl(i.getUrl()).orElse(null), Locale.forLanguageTag(i.getIso639Id())))
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }
}
