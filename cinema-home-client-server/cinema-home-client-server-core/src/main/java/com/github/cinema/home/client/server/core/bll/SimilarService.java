package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.MediaGroupService;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SimilarService {
    private final MediaGroupService mediaGroupService;
    private final ParallelMediaExtender parallelExtender;

    @Autowired
    public SimilarService(MediaGroupService mediaGroupService, ParallelMediaExtender parallelExtender) {
        this.mediaGroupService = mediaGroupService;
        this.parallelExtender = parallelExtender;
    }
    
    public List<MovieNfo> getSimilarMoviesFor(TmdbId id, int page) throws ServiceErrorException {
        List<MovieNfo> similars = this.mediaGroupService.findSimilarMoviesFor(id, page);
        return this.parallelExtender.doMovies(similars, this::hasNecessaryProperties);
    }

    public List<ShowNfo> getSimilarShowsFor(TmdbId id, int page) throws ServiceErrorException {
        List<ShowNfo> similars = this.mediaGroupService.findSimilarShowsFor(id, page);
        return this.parallelExtender.doShows(similars, this::hasNecessaryProperties);
    }

    private boolean hasNecessaryProperties(MediaNfo media) {
        return media.hasImdbId() && !CollectionUtils.isEmpty(media.getPosters())
                && (!CollectionUtils.isEmpty(media.getBackdrops()) || !CollectionUtils.isEmpty(media.getThumbs()));
    }
}
