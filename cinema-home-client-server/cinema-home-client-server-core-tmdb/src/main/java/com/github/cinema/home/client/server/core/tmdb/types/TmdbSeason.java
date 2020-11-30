package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.ShowSeasonNfo;
import com.github.cinema.home.client.server.common.utils.LocaleDetector;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TmdbSeason {
    private final Date airDate;
    private final Integer episodeCount;
    private final Integer id;
    private final String name;
    private final String overview;
    private final String posterPath;
    private final Integer seasonNo;
    private final List<TmdbEpisode> episodes;
    private final TmdbVideoResult videos;
    private final TmdbImagesResult images;

    private final LocaleDetector detector = new LocaleDetector();

    @JsonCreator
    private TmdbSeason(
            @JsonProperty("air_date") Date airDate,
            @JsonProperty("episode_count") Integer episodeCount,
            @JsonProperty("id") Integer id,
            @JsonProperty("name") String name,
            @JsonProperty("overview") String overview,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("season_number") Integer seasonNo,
            @JsonProperty("episodes") List<TmdbEpisode> episodes,
            @JsonProperty("videos") TmdbVideoResult videos,
            @JsonProperty("images") TmdbImagesResult images) {
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNo = seasonNo;
        this.episodes = episodes;
        this.videos = videos;
        this.images = images;
    }

    public Date getAirDate() {
        return this.airDate;
    }

    public Integer getEpisodeCount() {
        return this.episodes != null ? this.episodes.size() : this.episodeCount;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOverview() {
        return StringUtils.isEmpty(this.overview) ? null : this.overview;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public Integer getSeasonNo() {
        return this.seasonNo;
    }

    public TmdbVideoResult getVideos() {
        return this.videos;
    }

    public TmdbImagesResult getImages() {
        return this.images;
    }

    public List<TmdbEpisode> getEpisodes() {
        return this.episodes;
    }

    public ShowSeasonNfo toSeason() {
        return ShowSeasonNfo.builder()
                .seasonNumber(this.seasonNo)
                .name(StringUtils.isEmpty(this.name) ? null : LocaleString.locale(this.name, this.detector.detect(this.name)))
                .plot(StringUtils.isEmpty(this.overview) ? null : LocaleString.locale(this.overview, LocaleContextHolder.getLocale()))
                .airDate(this.airDate)
                .posters(this.images == null ? null : this.images.toPosterImages())
                .episodeCount(this.episodeCount)
                .episodes(this.episodes == null ? null : this.episodes.stream()
                        .map(TmdbEpisode::toEpisode)
                        .collect(Collectors.toList()))
                .build();
    }
}
