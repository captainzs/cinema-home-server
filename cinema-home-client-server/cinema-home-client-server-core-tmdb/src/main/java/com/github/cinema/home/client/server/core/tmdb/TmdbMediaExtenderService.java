package com.github.cinema.home.client.server.core.tmdb;

import com.github.cinema.home.client.server.common.ExtentDecider;
import com.github.cinema.home.client.server.common.MediaExtenderService;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TmdbMediaExtenderService implements MediaExtenderService {
    private final TmdbMediaService service;

    @Autowired
    public TmdbMediaExtenderService(TmdbMediaService service) {
        this.service = service;
    }

    @Override
    public MovieNfo extend(MovieNfo movie) throws ServiceErrorException {
        return this.extend(movie, b -> false);
    }

    @Override
    public MovieNfo extend(MovieNfo movie, ExtentDecider breakPoint) throws ServiceErrorException {
        MovieNfo actual = movie;
        if (!breakPoint.hasEnoughProperties(actual) && !actual.hasTmdbId()) {
            Optional<MovieNfo> basicNfo = Optional.empty();
            if (actual.hasImdbId()) {
                basicNfo = this.service.findMovie(actual.getImdbId());
            }
            if (!basicNfo.isPresent() && actual.hasTvdbId()) {
                basicNfo = this.service.findMovie(actual.getTvdbId());
            }
            if (!basicNfo.isPresent()) {
                basicNfo = this.service.findMovie(actual.getTitles().iterator().next().getStr(), actual.getReleaseYear());
            }
            if (basicNfo.isPresent()) {
                actual = MovieNfo.builder().build(actual, basicNfo.get());
            }
        }
        if (!breakPoint.hasEnoughProperties(actual) && actual.hasTmdbId()) {
            Optional<MovieNfo> tmdbDetailed = this.service.findMovie(actual.getTmdbId());
            if (tmdbDetailed.isPresent()) {
                actual = MovieNfo.builder().build(actual, tmdbDetailed.get());
            }
        }
        return actual;
    }

    @Override
    public ShowNfo extend(ShowNfo show) throws ServiceErrorException {
        return this.extend(show, b -> false);
    }

    @Override
    public ShowNfo extend(ShowNfo show, ExtentDecider breakPoint) throws ServiceErrorException {
        ShowNfo actual = show;
        if (!breakPoint.hasEnoughProperties(actual) && !actual.hasTmdbId()) {
            Optional<ShowNfo> basicNfo = Optional.empty();
            if (actual.hasImdbId()) {
                basicNfo = this.service.findShow(actual.getImdbId());
            }
            if (!basicNfo.isPresent() && actual.hasTvdbId()) {
                basicNfo = this.service.findShow(actual.getTvdbId());
            }
            if (!basicNfo.isPresent()) {
                basicNfo = this.service.findShow(actual.getTitles().iterator().next().getStr());
            }
            if (basicNfo.isPresent()) {
                actual = ShowNfo.builder().build(actual, basicNfo.get());
            }
        }
        if (!breakPoint.hasEnoughProperties(actual) && actual.hasTmdbId()) {
            Optional<ShowNfo> tmdbDetailed = this.service.findShow(actual.getTmdbId());
            if (tmdbDetailed.isPresent()) {
                actual = ShowNfo.builder().build(actual, tmdbDetailed.get());
            }
        }
        return actual;
    }
}
