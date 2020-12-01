package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.MediaGroupService;
import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.SortBy;
import com.github.cinema.home.client.server.common.types.SortOrder;
import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final SearchService searchService;
    private final MediaGroupService mediaGroupService;
    private final ParallelMediaExtender parallelExtender;

    @Autowired
    public NetworkService(SearchService searchService, MediaGroupService mediaGroupService, ParallelMediaExtender parallelExtender) {
        this.searchService = searchService;
        this.mediaGroupService = mediaGroupService;
        this.parallelExtender = parallelExtender;
    }
    
    public List<MovieNfo> searchMovies(NetworkGroup network, int page) throws ServiceErrorException, InvalidArgumentException {
        SearchFilter filter = SearchFilter.builder()
                .subText(network.getSearchLabel())
                .sortBy(SortBy.POPULARITY)
                .sortOrder(SortOrder.DESCENDING)
                .build();
        return this.searchService.searchMovies(filter, page);
    }

    public List<ShowNfo> searchShows(NetworkGroup network, int page) throws ServiceErrorException {
        List<ShowNfo> shows = this.mediaGroupService.findShowsOf(network, page);
        return this.parallelExtender.doShows(shows, this::hasNecessaryProperties).stream()
                .filter(ShowNfo::hasTorrent)
                .collect(Collectors.toList());
    }

    private boolean hasNecessaryProperties(MediaNfo media) {
        return media.hasImdbId() && !CollectionUtils.isEmpty(media.getPosters());
    }
}
