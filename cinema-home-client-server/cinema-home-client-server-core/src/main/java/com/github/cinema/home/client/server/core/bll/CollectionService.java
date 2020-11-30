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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionService {
    private final MediaGroupService mediaGroupService;
    private final ParallelMediaExtender parallelExtender;

    @Autowired
    public CollectionService(MediaGroupService mediaGroupService, ParallelMediaExtender parallelExtender) {
        this.mediaGroupService = mediaGroupService;
        this.parallelExtender = parallelExtender;
    }

    public List<MovieNfo> getMoviesForCollection(TmdbId collectionId) throws ServiceErrorException {
        List<MovieNfo> parts = this.mediaGroupService.findMoviesForCollection(collectionId).stream()
                .sorted(Comparator.comparing(MediaNfo::getReleaseYear))
                .collect(Collectors.toList());
        return this.parallelExtender.doMovies(parts, this::hasNecessaryProperties);
    }

    public List<ShowNfo> getShowsForCollection(TmdbId collectionId) throws ServiceErrorException {
        List<ShowNfo> parts = this.mediaGroupService.findShowsForCollection(collectionId).stream()
                .sorted(Comparator.comparing(MediaNfo::getReleaseYear))
                .collect(Collectors.toList());
        return this.parallelExtender.doShows(parts, this::hasNecessaryProperties);
    }

    private boolean hasNecessaryProperties(MediaNfo media) {
        return media.hasImdbId() && !CollectionUtils.isEmpty(media.getPosters())
                && (!CollectionUtils.isEmpty(media.getBackdrops()) || !CollectionUtils.isEmpty(media.getThumbs()));
    }
}
