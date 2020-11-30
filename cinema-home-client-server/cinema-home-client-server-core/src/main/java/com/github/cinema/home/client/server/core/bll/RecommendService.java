package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.MediaGroupService;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.tracker.api.TorrentBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendService {
    private final TorrentBrowser browser;
    private final MediaGroupService mediaGroupService;
    private final ParallelMediaExtender parallelExtender;

    @Autowired
    public RecommendService(TorrentBrowser browser, MediaGroupService mediaGroupService, ParallelMediaExtender parallelExtender) {
        this.browser = browser;
        this.mediaGroupService = mediaGroupService;
        this.parallelExtender = parallelExtender;
    }

    public List<MovieNfo> recommendMovies(int page) throws ServiceErrorException {
        List<MovieNfo> nfos = this.browser.recommendMovies(page);
        List<MovieNfo> extended = this.parallelExtender.doMovies(nfos, this::hasNecessaryProperties);
        Map<ImdbId, MovieNfo> uniques = new LinkedHashMap<>();
        for (MovieNfo nfo : extended) {
            if (uniques.containsKey(nfo.getImdbId())) {
                uniques.put(nfo.getImdbId(), MovieNfo.builder().build(nfo, uniques.getOrDefault(nfo.getImdbId(), null)));
            } else {
                uniques.put(nfo.getImdbId(), nfo);
            }
        }
        return new ArrayList<>(uniques.values());
    }

    public List<ShowNfo> recommendShows(int page) throws ServiceErrorException {
        List<ShowNfo> nfos = this.browser.recommendShows(page);
        List<ShowNfo> extended = this.parallelExtender.doShows(nfos, this::hasNecessaryProperties);
        Map<ImdbId, ShowNfo> uniques = new LinkedHashMap<>();
        for (ShowNfo nfo : extended) {
            if (uniques.containsKey(nfo.getImdbId())) {
                uniques.put(nfo.getImdbId(), ShowNfo.builder().build(nfo, uniques.getOrDefault(nfo.getImdbId(), null)));
            } else {
                uniques.put(nfo.getImdbId(), nfo);
            }
        }
        return new ArrayList<>(uniques.values());
    }

    public List<MovieNfo> recommendMoviesFor(TmdbId tmdbId, int page) throws ServiceErrorException {
        List<MovieNfo> nfos = this.mediaGroupService.findRecommendedMoviesFor(tmdbId, page);
        return this.parallelExtender.doMovies(nfos, this::hasNecessaryProperties).stream()
                .filter(MovieNfo::hasTorrent)
                .collect(Collectors.toList());
    }

    public List<ShowNfo> recommendShowsFor(TmdbId tmdbId, int page) throws ServiceErrorException {
        List<ShowNfo> nfos = this.mediaGroupService.findRecommendedShowsFor(tmdbId, page);
        return this.parallelExtender.doShows(nfos, this::hasNecessaryProperties).stream()
                .filter(ShowNfo::hasTorrent)
                .collect(Collectors.toList());
    }

    private boolean hasNecessaryProperties(MediaNfo media) {
        return media.hasImdbId() && !CollectionUtils.isEmpty(media.getPosters());
    }
}
