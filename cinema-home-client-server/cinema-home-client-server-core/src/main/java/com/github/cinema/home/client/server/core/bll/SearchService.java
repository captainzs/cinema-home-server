package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.tracker.api.TorrentBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final TorrentBrowser searcher;
    private final ParallelMediaExtender parallelExtender;

    @Autowired
    public SearchService(TorrentBrowser searcher, ParallelMediaExtender parallelExtender) {
        this.searcher = searcher;
        this.parallelExtender = parallelExtender;
    }

    public List<MovieNfo> searchMovies(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException {
        List<MovieNfo> nfos = this.searcher.findMovies(filter, page);
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

    public List<ShowNfo> searchShows(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException {
        List<ShowNfo> nfos = this.searcher.findShows(filter, page);
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

    private boolean hasNecessaryProperties(MediaNfo media) {
        return media.hasImdbId() && !CollectionUtils.isEmpty(media.getPosters());
    }
}
