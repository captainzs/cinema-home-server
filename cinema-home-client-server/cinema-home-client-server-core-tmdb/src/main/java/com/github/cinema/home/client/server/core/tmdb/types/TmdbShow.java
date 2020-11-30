package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.Video;
import com.github.cinema.home.client.server.common.types.media.VideoType;
import com.github.cinema.home.client.server.core.tmdb.config.ApiMappings;
import com.github.cinema.home.client.server.core.tmdb.config.NetworkApiMappings;
import com.github.cinema.home.client.server.core.tmdb.config.StatusApiMappings;
import com.github.cinema.home.client.server.core.tmdb.utils.WebUrlMaker;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class TmdbShow extends TmdbMedia {
    private final List<TmdbCreator> createdBy;
    private final Date firstAirDate;
    private final Boolean inProduction;
    private final List<String> languages;
    private final Date lastAirDate;
    private final TmdbEpisode lastEpisodeToAir;
    private final TmdbEpisode nextEpisodeToAir;
    private final Set<TmdbCompany> networks;
    private final Integer numberOfEpisodes;
    private final Integer numberOfSeasons;
    private final List<String> originCountry;
    private final List<Integer> episodeRuntimes;
    private final List<TmdbSeason> seasons;
    private final String type;

    @JsonCreator
    private TmdbShow(
            @JsonProperty("backdrop_path") String backdropPath,
            @JsonProperty("belongs_to_collection") TmdbCollection collection,
            @JsonProperty("genres") Set<TmdbGenre> genres,
            @JsonProperty("genre_ids") Set<Integer> genreIds,
            @JsonProperty("homepage") String homepage,
            @JsonProperty("id") TmdbId id,
            @JsonProperty("original_language") String originalLanguage,
            @JsonProperty("original_name") String originalTitle,
            @JsonProperty("overview") String overview,
            @JsonProperty("popularity") Double popularity,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("production_companies") List<TmdbCompany> productionCompanies,
            @JsonProperty("episode_run_time") List<Integer> episodeRuntimes,
            @JsonProperty("status") String status,
            @JsonProperty("name") String title,
            @JsonProperty("vote_average") Double voteAverage,
            @JsonProperty("vote_count") Integer voteCount,
            @JsonProperty("external_ids") TmdbExternalIdSet externalIds,
            @JsonProperty("videos") TmdbVideoResult videos,
            @JsonProperty("credits") TmdbCredits credits,
            @JsonProperty("images") TmdbImagesResult images,
            @JsonProperty("created_by") List<TmdbCreator> createdBy,
            @JsonProperty("first_air_date") Date firstAirDate,
            @JsonProperty("in_production") Boolean inProduction,
            @JsonProperty("languages") List<String> languages,
            @JsonProperty("last_air_date") Date lastAirDate,
            @JsonProperty("last_episode_to_air") TmdbEpisode lastEpisodeToAir,
            @JsonProperty("next_episode_to_air") TmdbEpisode nextEpisodeToAir,
            @JsonProperty("networks") Set<TmdbCompany> networks,
            @JsonProperty("number_of_episodes") Integer numberOfEpisodes,
            @JsonProperty("number_of_seasons") Integer numberOfSeasons,
            @JsonProperty("origin_country") List<String> originCountry,
            @JsonProperty("seasons") List<TmdbSeason> seasons,
            @JsonProperty("type") String type) {
        super(backdropPath, collection, genres, genreIds, homepage, id, originalLanguage, originalTitle, overview,
                popularity, posterPath, productionCompanies, status, title, voteAverage, voteCount, externalIds, videos,
                credits, images);
        this.createdBy = createdBy;
        this.firstAirDate = firstAirDate;
        this.inProduction = inProduction;
        this.languages = languages;
        this.lastAirDate = lastAirDate;
        this.lastEpisodeToAir = lastEpisodeToAir;
        this.nextEpisodeToAir = nextEpisodeToAir;
        this.networks = networks;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.originCountry = originCountry;
        this.episodeRuntimes = episodeRuntimes;
        this.seasons = seasons;
        this.type = type;
    }

    public List<TmdbCreator> getCreatedBy() {
        return this.createdBy;
    }

    public Date getFirstAirDate() {
        return this.firstAirDate;
    }

    public Boolean isInProduction() {
        return this.inProduction;
    }

    public List<String> getLanguages() {
        return this.languages;
    }

    public Date getLastAirDate() {
        return this.lastAirDate;
    }

    public TmdbEpisode getLastEpisodeToAir() {
        return this.lastEpisodeToAir;
    }

    public TmdbEpisode getNextEpisodeToAir() {
        return this.nextEpisodeToAir;
    }

    public Set<TmdbCompany> getNetworks() {
        return this.networks;
    }

    public Integer getNumberOfEpisodes() {
        return this.numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return this.numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return this.originCountry;
    }

    public List<TmdbSeason> getSeasons() {
        return this.seasons;
    }

    public List<Integer> getEpisodeRuntimes() {
        return this.episodeRuntimes;
    }

    public Double getAverageRuntime() {
        OptionalDouble average = this.episodeRuntimes.stream().mapToInt((x) -> x).average();
        return average.isPresent() ? average.getAsDouble() : null;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "TmdbShow{" +
                "createdBy=" + this.createdBy +
                ", firstAirDate=" + this.firstAirDate +
                ", inProduction=" + this.inProduction +
                ", languages=" + this.languages +
                ", lastAirDate=" + this.lastAirDate +
                ", lastEpisodeToAir=" + this.lastEpisodeToAir +
                ", nextEpisodeToAir=" + this.nextEpisodeToAir +
                ", networks=" + this.networks +
                ", numberOfEpisodes=" + this.numberOfEpisodes +
                ", numberOfSeasons=" + this.numberOfSeasons +
                ", originCountry=" + this.originCountry +
                ", episodeRuntimes=" + this.episodeRuntimes +
                ", seasons=" + this.seasons +
                ", type='" + this.type + '\'' +
                ", backdropPath='" + this.backdropPath + '\'' +
                ", collection=" + this.collection +
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
    public ShowNfo toInfo() {
        Calendar releaseYearCalendar = Calendar.getInstance();
        if (this.firstAirDate != null) {
            releaseYearCalendar.setTime(this.firstAirDate);
        }
        OptionalDouble runtimeAvg = this.episodeRuntimes == null ? OptionalDouble.empty() : this.episodeRuntimes.stream().mapToInt(i -> i).average();
        return ShowNfo.builder()
                .backdrops(this.images == null ? null : this.images.toBackdropImages())
                .collection(this.collection == null ? null : this.collection.toCollection())
                .genres(ApiMappings.toGenres(this.genres))
                .genres(ApiMappings.toGenresByIds(this.genreIds))
                .tmdbId(this.id)
                .title(StringUtils.isEmpty(this.title) ? null : LocaleString.locale(this.title, this.detector.detect(this.title)))
                .title(StringUtils.isEmpty(this.originalTitle) ? null : LocaleString.locale(this.originalTitle, Locale.forLanguageTag(this.originalLanguage)))
                .plot(StringUtils.isEmpty(this.overview) ? null : LocaleString.locale(this.overview, LocaleContextHolder.getLocale()))
                .posters(this.images == null ? null : this.images.toPosterImages())
                .status(StatusApiMappings.toShowStatus(this.status))
                .rating(this.voteAverage)
                .imdbId(this.externalIds != null ? this.externalIds.getImdbId() : null)
                .tvdbId(this.externalIds != null ? this.externalIds.getTvdbId() : null)
                .videos(this.videos == null ? null : this.videos.getResults().stream()
                        .map(v -> Video.builder()
                                .title(v.getName())
                                .iso639Id(v.getIso639Id())
                                .url(WebUrlMaker.getTmdbYoutubeUrl(v.getKey()).orElse(null))
                                .type(VideoType.of(v.getType()))
                                .build())
                        .filter(v -> v.getUrl() != null)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .creators(this.createdBy == null ? null : this.createdBy.stream()
                        .map(TmdbCreator::getName)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .actors(this.credits == null ? null : this.credits.getActors().stream()
                        .map(TmdbActor::toActor)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .releaseYear(releaseYearCalendar != null ? releaseYearCalendar.get(Calendar.YEAR) : null)
                .lastEpisode(this.lastEpisodeToAir == null ? null : this.lastEpisodeToAir.toEpisode())
                .nextEpisode(this.nextEpisodeToAir == null ? null : this.nextEpisodeToAir.toEpisode())
                .networks(this.networks == null ? null : NetworkApiMappings.toNetworks(this.networks))
                .seasonCount(this.numberOfSeasons)
                .episodeCount(this.numberOfEpisodes)
                .runtime(runtimeAvg.isPresent() ? runtimeAvg.getAsDouble() : null)
                .seasons(this.seasons == null ? null : this.seasons.stream()
                        .map(TmdbSeason::toSeason)
                        .collect(Collectors.toList()))
                .build();
    }
}
