package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.ExtentDecider;
import com.github.cinema.home.client.server.common.MediaExtenderService;
import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.core.fanart.FanartExtenderService;
import com.github.cinema.home.client.server.core.tmdb.TmdbMediaExtenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegratedMediaExtender {
    private static final Logger logger = LoggerFactory.getLogger(IntegratedMediaExtender.class);
    private final TmdbMediaExtenderService tmdbExtender;
    private final FanartExtenderService fanartExtender;

    @Autowired
    public IntegratedMediaExtender(TmdbMediaExtenderService tmdbExtender, FanartExtenderService fanartExtender) {
        this.tmdbExtender = tmdbExtender;
        this.fanartExtender = fanartExtender;
    }

    public MovieNfo extendFull(MovieNfo movie) {
        MovieNfo extended = this.tryService(this.tmdbExtender, movie);
        return this.tryService(this.fanartExtender, extended);
    }

    public MovieNfo extendUntil(MovieNfo movie, ExtentDecider until) throws InvalidArgumentException {
        MovieNfo extended = this.tryService(this.tmdbExtender, movie);
        if (!until.hasEnoughProperties(extended)) {
            extended = this.tryService(this.fanartExtender, extended);
        }
        if (!until.hasEnoughProperties(extended)) {
            throw new InvalidArgumentException(String.format("Can not gather enough information on the Movie: '%s'", movie));
        }
        return extended;
    }

    public ShowNfo extendFull(ShowNfo show) {
        ShowNfo extended = this.tryService(this.tmdbExtender, show);
        return this.tryService(this.fanartExtender, extended);
    }

    public ShowNfo extendUntil(ShowNfo show, ExtentDecider until) throws InvalidArgumentException {
        ShowNfo extended = this.tryService(this.tmdbExtender, show);
        if (!until.hasEnoughProperties(extended)) {
            extended = this.tryService(this.fanartExtender, extended);
        }
        if (!until.hasEnoughProperties(extended)) {
            throw new InvalidArgumentException(String.format("Can not gather enough information on the TV Show: '%s'", show));
        }
        return extended;
    }

    private MovieNfo tryService(MediaExtenderService service, MovieNfo movie) {
        try {
            return service.extend(movie);
        } catch (ServiceErrorException e) {
            logger.warn(String.format("Service is not available therefore movie extension will possibly fail! ('%s')", movie), e);
        }
        return movie;
    }

    private ShowNfo tryService(MediaExtenderService service, ShowNfo show) {
        try {
            return service.extend(show);
        } catch (ServiceErrorException e) {
            logger.warn(String.format("Service is not available therefore show extension will possibly fail! ('%s')", show), e);
        }
        return show;
    }
}
