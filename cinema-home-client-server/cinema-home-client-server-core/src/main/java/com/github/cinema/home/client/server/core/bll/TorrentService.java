package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.core.bll.comparators.DubbedComparator;
import com.github.cinema.home.client.server.core.bll.comparators.QualityComparator;
import com.github.cinema.home.client.server.core.bll.comparators.QualityShowComparator;
import com.github.cinema.home.client.server.core.bll.comparators.SeedsComparator;
import com.github.cinema.home.client.server.core.dal.PreferenceRepository;
import com.github.cinema.home.client.server.core.types.ReleasePreference;
import com.github.cinema.home.client.server.tracker.api.TorrentBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TorrentService {
    private final TorrentBrowser browser;
    private final PreferenceRepository repository;
    private final DetailsService detailsService;

    @Autowired
    public TorrentService(TorrentBrowser browser, PreferenceRepository repository, DetailsService detailsService) {
        this.browser = browser;
        this.repository = repository;
        this.detailsService = detailsService;
    }

    public MovieNfo getMovieTorrents(ImdbId id) throws ServiceErrorException, InvalidArgumentException {
        List<MovieNfo> nfos = new ArrayList<>();
        for (int page = 1; ; page++) {
            Optional<MovieNfo> nfo = this.browser.findMovie(id, page);
            if (!nfo.isPresent()) {
                break;
            }
            nfos.add(nfo.get());
        }
        MovieNfo merged = MovieNfo.builder().build(nfos.toArray(new MovieNfo[]{}));
        ReleasePreference preference = this.repository.findPreference();
        Comparator<TorrentNfo> mixedComparator;
        if (preference.isHunDub()) {
            mixedComparator = new DubbedComparator().thenComparing(new SeedsComparator()).thenComparing(new QualityComparator(preference.getResolutionDefinition()));
        } else {
            mixedComparator = new SeedsComparator().thenComparing(new QualityComparator(preference.getResolutionDefinition()));
        }
        merged.sortTorrentsBy(mixedComparator);
        return merged;
    }

    //TODO make DVD formats less preferable because you must download the whole package even if you want 1 episode
    public ShowNfo getShowTorrents(ImdbId id) throws ServiceErrorException, InvalidArgumentException {
        List<ShowNfo> nfos = new ArrayList<>();
        nfos.add(this.detailsService.detailShowWithSeasons(id));
        for (int page = 1; ; page++) {
            Optional<ShowNfo> nfo = this.browser.findShow(id, page);
            if (!nfo.isPresent()) {
                break;
            }
            nfos.add(nfo.get());
        }
        ShowNfo merged = ShowNfo.builder().build(nfos.toArray(new ShowNfo[]{}));
        ReleasePreference preference = this.repository.findPreference();
        Comparator<TorrentNfo> mixedComparator;
        if (preference.isHunDub()) {
            mixedComparator = new DubbedComparator()
                    .thenComparing(new SeedsComparator())
                    .thenComparing(new QualityShowComparator(preference.getResolutionDefinition()));
        } else {
            mixedComparator = new SeedsComparator()
                    .thenComparing(new QualityShowComparator(preference.getResolutionDefinition()));
        }
        merged.sortTorrentsBy(mixedComparator);
        return merged;
    }
}
