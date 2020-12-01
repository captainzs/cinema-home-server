package com.github.cinema.home.client.server.core.tmdb.types;

import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.utils.LocaleDetector;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

// TODO make every getter Optional<>
public abstract class TmdbMedia {
    protected final String backdropPath;
    protected final TmdbCollection collection;
    protected final Set<TmdbGenre> genres;
    protected final Set<Integer> genreIds;
    protected final String homepage;
    protected final TmdbId id;
    protected final String originalLanguage;
    protected final String originalTitle;
    protected final String overview;
    protected final Double popularity;
    protected final String posterPath;
    protected final List<TmdbCompany> productionCompanies;
    protected final String status;
    protected final String title;
    protected final Double voteAverage;
    protected final Integer voteCount;
    protected final TmdbExternalIdSet externalIds;
    protected final TmdbVideoResult videos;
    protected final TmdbCredits credits;
    protected final TmdbImagesResult images;

    protected final LocaleDetector detector = new LocaleDetector();

    protected TmdbMedia(String backdropPath, TmdbCollection collection, Set<TmdbGenre> genres, Set<Integer> genreIds,
                        String homepage, TmdbId id, String originalLanguage, String originalTitle, String overview,
                        Double popularity, String posterPath, List<TmdbCompany> productionCompanies, String status,
                        String title, Double voteAverage, Integer voteCount, TmdbExternalIdSet externalIds,
                        TmdbVideoResult videos, TmdbCredits credits, TmdbImagesResult images) {
        this.backdropPath = backdropPath;
        this.collection = collection;
        this.genres = genres;
        this.genreIds = genreIds;
        this.homepage = homepage;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.status = status;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.externalIds = externalIds;
        this.videos = videos;
        this.credits = credits;
        this.images = images;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public TmdbCollection getCollection() {
        return this.collection;
    }

    public Set<TmdbGenre> getGenres() {
        return this.genres;
    }

    public Set<Integer> getGenreIds() {
        return this.genreIds;
    }

    public String getHomepage() {
        return StringUtils.isEmpty(this.homepage) ? null : this.homepage;
    }

    public TmdbId getId() {
        return this.id;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getOverview() {
        return this.overview;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public List<TmdbCompany> getProductionCompanies() {
        return this.productionCompanies;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTitle() {
        return this.title;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public TmdbExternalIdSet getExternalIds() {
        return this.externalIds;
    }

    public TmdbVideoResult getVideos() {
        return this.videos;
    }

    public TmdbCredits getCredits() {
        return this.credits;
    }

    public TmdbImagesResult getImages() {
        return this.images;
    }

    public abstract MediaNfo toInfo() throws MalformedURLException;
}
