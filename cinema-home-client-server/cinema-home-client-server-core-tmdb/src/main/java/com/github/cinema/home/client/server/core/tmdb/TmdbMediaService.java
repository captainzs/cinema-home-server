package com.github.cinema.home.client.server.core.tmdb;

import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.ShowSeasonNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import com.github.cinema.home.client.server.core.tmdb.api.DetailsApi;
import com.github.cinema.home.client.server.core.tmdb.api.FindApi;
import com.github.cinema.home.client.server.core.tmdb.api.SearchApi;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;

//TODO test with unknown tmdb-ids
@Service
public class TmdbMediaService {
    private static final Logger logger = LoggerFactory.getLogger(TmdbMediaService.class);
    private final SearchApi searchApi;
    private final FindApi findApi;
    private final DetailsApi detailsApi;

    @Autowired
    public TmdbMediaService(SearchApi searchApi, FindApi findApi, DetailsApi detailsApi) {
        this.searchApi = searchApi;
        this.findApi = findApi;
        this.detailsApi = detailsApi;
    }

    public Optional<MovieNfo> findMovie(String title, Integer releaseYear) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbMovie> results = this.searchApi.requestMovies(title, releaseYear, 1).getResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            TmdbMovie preferred = releaseYear == null ?
                    results.get(0) :
                    results.stream().max(Comparator.comparing(TmdbMovie::getPopularity)).get();
            return Optional.of(preferred.toInfo());
        });
    }

    public Optional<MovieNfo> findMovie(ImdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbMovie> results = this.findApi.requestMedias(id).getMovieResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(results.get(0).toInfo());
        });
    }

    public Optional<MovieNfo> findMovie(TvdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbMovie> results = this.findApi.requestMedias(id).getMovieResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(results.get(0).toInfo());
        });
    }

    public Optional<MovieNfo> findMovie(TmdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            Locale givenLocale = LocaleContextHolder.getLocale();
            if (!givenLocale.getLanguage().equals("en")) {
                // We have to query twice, because locale filters youtube videos,titles,plots and taglines
                MovieNfo givenLocaleNfo = this.detailsApi.requestMovie(id, givenLocale).toInfo();
                MovieNfo defaultLocaleNfo = this.detailsApi.requestMovie(id, Locale.ENGLISH).toInfo();
                return Optional.of(MovieNfo.builder().build(givenLocaleNfo, defaultLocaleNfo));
            }
            return Optional.of(this.detailsApi.requestMovie(id, givenLocale).toInfo());
        });
    }

    public Optional<ShowNfo> findShow(String title) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbShow> results = this.searchApi.requestShows(title, 1).getResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(results.get(0).toInfo());
        });
    }

    public Optional<ShowNfo> findShow(ImdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbShow> results = this.findApi.requestMedias(id).getShowResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(results.get(0).toInfo());
        });
    }

    public Optional<ShowNfo> findShow(TvdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            List<TmdbShow> results = this.findApi.requestMedias(id).getShowResults();
            if (results.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(results.get(0).toInfo());
        });
    }

    public Optional<ShowNfo> findShow(TmdbId id) throws ServiceErrorException {
        return this.tryService(() -> {
            Locale givenLocale = LocaleContextHolder.getLocale();
            if (!givenLocale.getLanguage().equals("en")) {
                // We have to query twice, because locale filters youtube videos,titles,plots and taglines
                ShowNfo givenLocaleNfo = this.detailsApi.requestShow(id, givenLocale).toInfo();
                ShowNfo defaultLocaleNfo = this.detailsApi.requestShow(id, Locale.ENGLISH).toInfo();
                return Optional.of(ShowNfo.builder().build(givenLocaleNfo, defaultLocaleNfo));
            }
            return Optional.of(this.detailsApi.requestShow(id, givenLocale).toInfo());
        });
    }

    public Optional<ShowSeasonNfo> findShowSeason(TmdbId id, int seasonNumber) throws ServiceErrorException {
        return this.tryService(() -> {
            Locale givenLocale = LocaleContextHolder.getLocale();
            if (!givenLocale.getLanguage().equals("en")) {
                // We have to query twice, because locale filters videos,plots and images
                ShowSeasonNfo givenLocaleNfo = this.detailsApi.requestSeason(id, seasonNumber, givenLocale).toSeason();
                ShowSeasonNfo defaultLocaleNfo = this.detailsApi.requestSeason(id, seasonNumber, Locale.ENGLISH).toSeason();
                return Optional.of(ShowSeasonNfo.builder().build(givenLocaleNfo, defaultLocaleNfo));
            }
            return Optional.of(this.detailsApi.requestSeason(id, seasonNumber, givenLocale).toSeason());
        });
    }

    private <T> Optional<T> tryService(Callable<Optional<T>> function) throws ServiceErrorException {
        try {
            return function.call();
        } catch (HttpServerErrorException e) {
            throw new ServiceErrorException("TMDB Api is unreachable! Try again later.", e);
        } catch (Exception e) {
            logger.error("TMDB service failed!", e);
            return Optional.empty();
        }
    }
}
