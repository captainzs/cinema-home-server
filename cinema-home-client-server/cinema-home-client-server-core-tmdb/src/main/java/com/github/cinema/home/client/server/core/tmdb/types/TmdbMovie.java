package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.Video;
import com.github.cinema.home.client.server.common.types.media.VideoType;
import com.github.cinema.home.client.server.core.tmdb.config.ApiMappings;
import com.github.cinema.home.client.server.core.tmdb.config.StatusApiMappings;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class TmdbMovie extends TmdbMedia {
    private final Boolean adult;
    private final Long budget;
    private final Set<TmdbCountry> productionCountries;
    private final Date releaseDate;
    private final Long revenue;
    private final Integer runtime;
    private final String tagline;
    private final Boolean video;

    @JsonCreator
    private TmdbMovie(
            @JsonProperty("backdrop_path") String backdropPath,
            @JsonProperty("belongs_to_collection") TmdbCollection collection,
            @JsonProperty("genres") Set<TmdbGenre> genres,
            @JsonProperty("genre_ids") Set<Integer> genreIds,
            @JsonProperty("homepage") String homepage,
            @JsonProperty("id") TmdbId id,
            @JsonProperty("original_language") String originalLanguage,
            @JsonProperty("original_title") String originalTitle,
            @JsonProperty("overview") String overview,
            @JsonProperty("popularity") Double popularity,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("production_companies") List<TmdbCompany> productionCompanies,
            @JsonProperty("runtime") Integer runtime,
            @JsonProperty("status") String status,
            @JsonProperty("title") String title,
            @JsonProperty("vote_average") Double voteAverage,
            @JsonProperty("vote_count") Integer voteCount,
            @JsonProperty("external_ids") TmdbExternalIdSet externalIds,
            @JsonProperty("videos") TmdbVideoResult videos,
            @JsonProperty("credits") TmdbCredits credits,
            @JsonProperty("images") TmdbImagesResult images,
            @JsonProperty("production_countries") Set<TmdbCountry> productionCountries,
            @JsonProperty("adult") Boolean adult,
            @JsonProperty("budget") Long budget,
            @JsonProperty("release_date") Date releaseDate,
            @JsonProperty("revenue") Long revenue,
            @JsonProperty("tagline") String tagline,
            @JsonProperty("video") Boolean video) {
        super(backdropPath, collection, genres, genreIds, homepage, id, originalLanguage, originalTitle, overview,
                popularity, posterPath, productionCompanies, status, title, voteAverage, voteCount, externalIds, videos,
                credits, images);
        this.adult = adult;
        this.budget = budget;
        this.productionCountries = productionCountries;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.tagline = tagline;
        this.video = video;
    }

    public Boolean isAdult() {
        return this.adult;
    }

    public Long getBudget() {
        return this.budget;
    }

    public Set<TmdbCountry> getProductionCountries() {
        return this.productionCountries;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public Long getRevenue() {
        return this.revenue;
    }

    public Integer getRuntime() {
        return this.runtime;
    }

    public String getTagline() {
        return this.tagline;
    }

    public Boolean isVideo() {
        return this.video;
    }

    @Override
    public String toString() {
        return "TmdbMovie{" +
                "adult=" + this.adult +
                ", budget=" + this.budget +
                ", collection=" + this.collection +
                ", releaseDate=" + this.releaseDate +
                ", revenue=" + this.revenue +
                ", tagline='" + this.tagline + '\'' +
                ", video=" + this.video +
                ", backdropPath='" + this.backdropPath + '\'' +
                ", genres=" + this.genres +
                ", genreIds=" + this.genreIds +
                ", homepage='" + this.homepage + '\'' +
                ", id=" + this.id +
                ", originalLanguage='" + this.originalLanguage + '\'' +
                ", originalTitle='" + this.originalTitle + '\'' +
                ", overview='" + this.overview + '\'' +
                ", popularity=" + this.popularity +
                ", posterPath='" + this.posterPath + '\'' +
                ", productionCompanies=" + this.productionCompanies +
                ", productionCountries=" + this.productionCountries +
                ", runtime=" + this.runtime +
                ", status='" + this.status + '\'' +
                ", title='" + this.title + '\'' +
                ", voteAverage=" + this.voteAverage +
                ", voteCount=" + this.voteCount +
                ", externalIds=" + this.externalIds +
                ", videos=" + this.videos +
                ", credits=" + this.credits +
                ", images=" + this.images +
                '}';
    }

    @Override
    public MovieNfo toInfo() {
        Calendar releaseYearCalendar = Calendar.getInstance();
        if (this.releaseDate != null) {
            releaseYearCalendar.setTime(this.releaseDate);
        }

        return MovieNfo.builder()
                .backdrops(this.images == null ? null : this.images.toBackdropImages())
                .collection(this.collection == null ? null : this.collection.toCollection())
                .genres(ApiMappings.toGenres(this.genres))
                .genres(ApiMappings.toGenresByIds(this.genreIds))
                .tmdbId(this.id)
                .title(StringUtils.isEmpty(this.title) ? null : LocaleString.locale(this.title, this.detector.detect(this.title)))
                .title(StringUtils.isEmpty(this.originalTitle) ? null : LocaleString.locale(this.originalTitle, Locale.forLanguageTag(this.originalLanguage)))
                .plot(StringUtils.isEmpty(this.overview) ? null : LocaleString.locale(this.overview, LocaleContextHolder.getLocale()))
                .posters(this.images == null ? null : this.images.toPosterImages())
                .status(StatusApiMappings.toMovieStatus(this.status))
                .rating(this.voteAverage)
                .imdbId(this.externalIds != null ? this.externalIds.getImdbId() : null)
                .tvdbId(this.externalIds != null ? this.externalIds.getTvdbId() : null)
                .videos(this.videos == null ? null : this.videos.getResults().stream()
                        .map(v -> Video.builder()
                                .title(v.getName())
                                .iso639Id(v.getIso639Id())
                                .url(WebUrlMaker.getTmdbYoutubeUrl(v.getKey()).orElse(null))
                                .type(VideoType.of(v.getType()))  //TODO checkold le a BehindTheScenest hogy megy e
                                .build())
                        .filter(v -> v.getUrl() != null)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .creators(this.credits == null ? null : this.credits.getCrew().stream()
                        .filter(c -> "director".equals(c.getJob().toLowerCase()))
                        .map(TmdbCreator::getName)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .actors(this.credits == null ? null : this.credits.getActors().stream()
                        .map(TmdbActor::toActor)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .releaseYear(releaseYearCalendar != null ? releaseYearCalendar.get(Calendar.YEAR) : null)
                .runtime(this.runtime == null ? null : this.runtime.doubleValue())
                .tagline(StringUtils.isEmpty(this.tagline) ? null : LocaleString.locale(this.tagline, LocaleContextHolder.getLocale()))
                .build();
    }
}
