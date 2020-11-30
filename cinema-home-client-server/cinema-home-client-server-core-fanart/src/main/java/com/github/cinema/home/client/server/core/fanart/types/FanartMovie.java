package com.github.cinema.home.client.server.core.fanart.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.fanart.utils.WebUrlMaker;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FanartMovie extends FanartMedia {
    private final Integer tmdbId;
    private final String imdbId;
    private final List<FanartDiscImage> discs;

    @JsonCreator
    private FanartMovie(
            @JsonProperty("name") String name,
            @JsonProperty("tmdb_id") Integer tmdbId,
            @JsonProperty("imdb_id") String imdbId,
            @JsonProperty("hdmovieclearart") List<FanartImage> hdClearArts,
            @JsonProperty("hdmovielogo") List<FanartImage> hdClearLogos,
            @JsonProperty("movieposter") List<FanartImage> posters,
            @JsonProperty("moviebackground") List<FanartImage> backgrounds,
            @JsonProperty("moviedisc") List<FanartDiscImage> discs,
            @JsonProperty("moviethumb") List<FanartImage> thumbs,
            @JsonProperty("movieart") List<FanartImage> clearArts,
            @JsonProperty("moviebanner") List<FanartImage> banners,
            @JsonProperty("movielogo") List<FanartImage> clearLogos) {
        super(name, hdClearArts, hdClearLogos, posters, backgrounds, thumbs, clearArts, banners, clearLogos);
        this.tmdbId = tmdbId;
        this.imdbId = imdbId;
        this.discs = discs;
    }

    public TmdbId getTmdbId() {
        return this.tmdbId != null ? TmdbId.of(this.tmdbId) : null;
    }

    public ImdbId getImdbId() {
        return !StringUtils.isEmpty(this.imdbId) ? ImdbId.of(this.imdbId) : null;
    }

    public List<FanartDiscImage> getDiscs() {
        return this.discs;
    }

    @Override
    public String toString() {
        return "FanartResult{" +
                "name='" + this.name + '\'' +
                ", tmdbId='" + this.tmdbId + '\'' +
                ", imdbId='" + this.imdbId + '\'' +
                ", hdClearArts=" + this.hdClearArts +
                ", hdClearLogos=" + this.hdClearLogos +
                ", posters=" + this.posters +
                ", backgrounds=" + this.backgrounds +
                ", discs=" + this.discs +
                ", thumbs=" + this.thumbs +
                ", clearArts=" + this.clearArts +
                ", banners=" + this.banners +
                ", clearLogos=" + this.clearLogos +
                '}';
    }

    @Override
    public MovieNfo toNfo() {
        return MovieNfo.builder()
                .title(LocaleString.locale(this.name, Locale.ENGLISH))
                .tmdbId(this.getTmdbId())
                .imdbId(this.getImdbId())
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
