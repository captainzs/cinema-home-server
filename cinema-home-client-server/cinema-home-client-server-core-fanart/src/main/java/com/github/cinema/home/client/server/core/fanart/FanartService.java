package com.github.cinema.home.client.server.core.fanart;

import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;
import java.util.concurrent.Callable;

@Service
public class FanartService {
    private final FanartApiService apiService;

    @Autowired
    public FanartService(FanartApiService apiService) {
        this.apiService = apiService;
    }

    public Optional<MovieNfo> findMovieArts(TmdbId tmdbId) throws ServiceErrorException {
        return this.tryService(() -> Optional.of(this.apiService.requestMovieArts(tmdbId).toNfo()));
    }

    public Optional<MovieNfo> findMovieArts(ImdbId imdbId) throws ServiceErrorException {
        return this.tryService(() -> Optional.of(this.apiService.requestMovieArts(imdbId).toNfo()));
    }

    public Optional<ShowNfo> findShowArts(TvdbId tvdbId) throws ServiceErrorException {
        return this.tryService(() -> Optional.of(this.apiService.requestShowArts(tvdbId).toNfo()));
    }

    private <T> Optional<T> tryService(Callable<Optional<T>> function) throws ServiceErrorException {
        try {
            return function.call();
        } catch (HttpServerErrorException e) {
            throw new ServiceErrorException("FanArt Api is unreachable! Try again later.", e);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
