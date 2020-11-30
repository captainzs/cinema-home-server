package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaType;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.tracker.api.TorrentBrowser;
import com.github.cinema.home.client.server.tracker.ncore.parsers.RecommendationParser;
import com.github.cinema.home.client.server.tracker.ncore.parsers.SearchParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NcoreTorrentBrowser implements TorrentBrowser {
    private final UrlMaker urlMaker;
    private final PageLoader pageLoader;
    private final SearchParser searchParser;
    private final RecommendationParser recommendationParser;

    @Autowired
    public NcoreTorrentBrowser(UrlMaker urlMaker, PageLoader pageLoader, SearchParser searchParser,
                               RecommendationParser recommendationParser) {
        this.urlMaker = urlMaker;
        this.pageLoader = pageLoader;
        this.searchParser = searchParser;
        this.recommendationParser = recommendationParser;
    }

    @Override
    public List<MovieNfo> findMovies(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException {
        URL url = this.urlMaker.searchUrl(filter, page, MediaType.MOVIE);
        try {
            Document html = this.pageLoader.load(url);
            return this.searchParser.parseMovies(html);
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
    }

    @Override
    public List<ShowNfo> findShows(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException {
        URL url = this.urlMaker.searchUrl(filter, page, MediaType.SHOW);
        try {
            Document html = this.pageLoader.load(url);
            return this.searchParser.parseShows(html);
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
    }

    @Override
    public Optional<MovieNfo> findMovie(ImdbId id, int page) throws ServiceErrorException, InvalidArgumentException {
        MovieNfo result = null;
        URL url = this.urlMaker.searchUrl(id, page, MediaType.MOVIE);
        try {
            Document html = this.pageLoader.load(url);
            List<MovieNfo> nfos = this.searchParser.parseMovies(html);
            for (MovieNfo nfo : nfos) {
                result = MovieNfo.builder().build(result, nfo);
            }
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ShowNfo> findShow(ImdbId id, int page) throws ServiceErrorException, InvalidArgumentException {
        ShowNfo result = null;
        URL url = this.urlMaker.searchUrl(id, page, MediaType.SHOW);
        try {
            Document html = this.pageLoader.load(url);
            List<ShowNfo> nfos = this.searchParser.parseShows(html);
            for (ShowNfo nfo : nfos) {
                result = ShowNfo.builder().build(result, nfo);
            }
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<MovieNfo> recommendMovies(int page) throws ServiceErrorException {
        if (page > 2) {
            return new ArrayList<>(0);
        }
        URL url = this.urlMaker.recommendedUrl();
        try {
            Document html = this.pageLoader.load(url);
            List<MovieNfo> all = this.recommendationParser.parseMovies(html);
            if (page <= 1) {
                return all.subList(0, 9);
            }
            return all.subList(9, all.size());
        } catch (InvalidArgumentException e) {
            throw new ServiceErrorException("Ncore document format has changed!", e);
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
    }

    @Override
    public List<ShowNfo> recommendShows(int page) throws ServiceErrorException {
        if (page > 2) {
            return new ArrayList<>(0);
        }
        URL url = this.urlMaker.recommendedUrl();
        try {
            Document html = this.pageLoader.load(url);
            List<ShowNfo> all = this.recommendationParser.parseShows(html);
            if (page <= 1) {
                return all.subList(0, 9);
            }
            return all.subList(9, all.size());
        } catch (InvalidArgumentException e) {
            throw new ServiceErrorException("Ncore document format has changed!", e);
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", url), e);
        }
    }
}
