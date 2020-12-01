package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.ShowEpisodeNfo;
import com.github.cinema.home.client.server.common.utils.LocaleDetector;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

public class TmdbEpisode {
    private final Date airDate;
    private final Integer episodeNo;
    private final Integer id;
    private final String name;
    private final String overview;
    private final String productionCode;
    private final Integer seasonNo;
    private final Integer showId;
    private final String stillPath;
    private final Double voteAverage;
    private final Integer voteCount;
    private final List<TmdbCreator> crew;
    private final List<TmdbActor> guestStars;

    private final LocaleDetector detector = new LocaleDetector();

    @JsonCreator
    private TmdbEpisode(
            @JsonProperty("air_date") Date airDate,
            @JsonProperty("episode_number") Integer episodeNo,
            @JsonProperty("id") Integer id,
            @JsonProperty("name") String name,
            @JsonProperty("overview") String overview,
            @JsonProperty("production_code") String productionCode,
            @JsonProperty("season_number") Integer seasonNo,
            @JsonProperty("show_id") Integer showId,
            @JsonProperty("still_path") String stillPath,
            @JsonProperty("vote_average") Double voteAverage,
            @JsonProperty("vote_count") Integer voteCount,
            @JsonProperty("crew") List<TmdbCreator> crew,
            @JsonProperty("guest_stars") List<TmdbActor> guestStars) {
        this.airDate = airDate;
        this.episodeNo = episodeNo;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.productionCode = productionCode;
        this.seasonNo = seasonNo;
        this.showId = showId;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.crew = crew;
        this.guestStars = guestStars;
    }

    public Date getAirDate() {
        return this.airDate;
    }

    public Integer getEpisodeNo() {
        return this.episodeNo;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getProductionCode() {
        return this.productionCode;
    }

    public Integer getSeasonNo() {
        return this.seasonNo;
    }

    public Integer getShowId() {
        return this.showId;
    }

    public String getStillPath() {
        return this.stillPath;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public List<TmdbCreator> getCrew() {
        return this.crew;
    }

    public List<TmdbActor> getGuestStars() {
        return this.guestStars;
    }

    public ShowEpisodeNfo toEpisode() {
        return ShowEpisodeNfo.builder()
                .airDate(this.airDate)
                .seasonNumber(this.seasonNo)
                .episodeNumber(this.episodeNo)
                .name(StringUtils.isEmpty(this.name) ? null : LocaleString.locale(this.name, this.detector.detect(this.name)))
                .plot(StringUtils.isEmpty(this.overview) ? null : LocaleString.locale(this.overview, LocaleContextHolder.getLocale()))
                .still(this.stillPath == null ? null : Image.clear(WebUrlMaker.getTmdbImageUrl(this.stillPath, "original").orElse(null)))
                .rating(this.voteAverage)
                .build();
    }
}
