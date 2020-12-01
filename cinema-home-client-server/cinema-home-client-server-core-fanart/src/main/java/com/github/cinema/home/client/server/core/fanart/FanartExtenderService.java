package com.github.cinema.home.client.server.core.fanart;

import com.github.cinema.home.client.server.common.ExtentDecider;
import com.github.cinema.home.client.server.common.MediaExtenderService;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FanartExtenderService implements MediaExtenderService {
    private final FanartService service;

    @Autowired
    public FanartExtenderService(FanartService service) {
        this.service = service;
    }

    @Override
    public MovieNfo extend(MovieNfo movie) throws ServiceErrorException {
        return this.extend(movie, b -> false);
    }

    @Override
    public MovieNfo extend(MovieNfo movie, ExtentDecider breakPoint) throws ServiceErrorException {
        MovieNfo actual = movie;
        if (!breakPoint.hasEnoughProperties(actual)) {
            Optional<MovieNfo> fanartNfo = Optional.empty();
            if (actual.hasImdbId()) {
                fanartNfo = this.service.findMovieArts(actual.getImdbId());
            }
            if (!fanartNfo.isPresent() && actual.hasTmdbId()) {
                fanartNfo = this.service.findMovieArts(actual.getTmdbId());
            }
            if (fanartNfo.isPresent()) {
                actual = MovieNfo.builder().build(actual, fanartNfo.get());
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
        if (!breakPoint.hasEnoughProperties(actual) && actual.hasTvdbId()) {
            Optional<ShowNfo> fanartNfo = this.service.findShowArts(actual.getTvdbId());
            if (fanartNfo.isPresent()) {
                actual = ShowNfo.builder().build(actual, fanartNfo.get());
            }
        }
        return actual;
    }
}
