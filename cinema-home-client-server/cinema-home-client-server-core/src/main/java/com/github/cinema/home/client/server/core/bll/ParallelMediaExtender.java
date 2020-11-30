package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.ExtentDecider;
import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.tracker.api.TorrentBrowser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class ParallelMediaExtender {
    private static final Logger logger = LoggerFactory.getLogger(ParallelMediaExtender.class);
    private static final int MAX_DOWNLOAD_SECS = 20;

    private final TorrentBrowser browser;
    private final IntegratedMediaExtender extender;

    @Autowired
    public ParallelMediaExtender(TorrentBrowser browser, IntegratedMediaExtender extender) {
        this.browser = browser;
        this.extender = extender;
    }

    public List<MovieNfo> doMovies(List<MovieNfo> nfos, ExtentDecider until) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<MovieNfo>> futures = new ArrayList<>();
        for (MovieNfo nfo : nfos) {
            futures.add(executor.submit(this.movieTask(nfo, until)));
        }
        List<MovieNfo> result = new ArrayList<>();
        for (Future<MovieNfo> future : futures) {
            try {
                MovieNfo nfo = future.get(MAX_DOWNLOAD_SECS, TimeUnit.SECONDS);
                if (nfo != null) {
                    result.add(nfo);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.warn(String.format("Movie is skipped: '%s'", future), e);
            }
        }
        return result;
    }

    public List<ShowNfo> doShows(List<ShowNfo> nfos, ExtentDecider until) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<ShowNfo>> futures = new ArrayList<>();
        for (ShowNfo nfo : nfos) {
            futures.add(executor.submit(this.showTask(nfo, until)));
        }
        List<ShowNfo> result = new ArrayList<>();
        for (Future<ShowNfo> future : futures) {
            try {
                ShowNfo nfo = future.get(MAX_DOWNLOAD_SECS, TimeUnit.SECONDS);
                if (nfo != null) {
                    result.add(nfo);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.warn(String.format("Show is skipped: '%s'", future), e);
            }
        }
        return result;
    }

    private Callable<MovieNfo> movieTask(final MovieNfo nfo, ExtentDecider until) {
        return () -> {
            try {
                MovieNfo extended = this.extender.extendUntil(nfo, until);
                if (!extended.hasTorrent()) {
                    Optional<MovieNfo> nfoWithTorrents = this.browser.findMovie(extended.getImdbId(), 1);
                    if (nfoWithTorrents.isPresent()) {
                        return MovieNfo.builder().build(extended, nfoWithTorrents.get());
                    }
                }
                return extended;
            } catch (InvalidArgumentException e) {
                throw new InvalidArgumentException(String.format("Extending movie failed: '%s'", nfo), e);
            }
        };
    }

    private Callable<ShowNfo> showTask(final ShowNfo nfo, ExtentDecider until) {
        return () -> {
            try {
                ShowNfo extended = this.extender.extendUntil(nfo, until);
                if (!extended.hasTorrent()) {
                    Optional<ShowNfo> nfoWithTorrents = this.browser.findShow(extended.getImdbId(), 1);
                    if (nfoWithTorrents.isPresent()) {
                        return ShowNfo.builder().build(extended, nfoWithTorrents.get());
                    }
                }
                return extended;
            } catch (InvalidArgumentException e) {
                throw new InvalidArgumentException(String.format("Extending show failed: '%s'", nfo), e);
            }
        };
    }
}
