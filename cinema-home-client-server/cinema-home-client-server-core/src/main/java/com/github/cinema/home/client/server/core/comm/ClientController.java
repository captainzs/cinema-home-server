package com.github.cinema.home.client.server.core.comm;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.bll.CollectionService;
import com.github.cinema.home.client.server.core.bll.DetailsService;
import com.github.cinema.home.client.server.core.bll.DownloadService;
import com.github.cinema.home.client.server.core.bll.NetworkService;
import com.github.cinema.home.client.server.core.bll.RecommendService;
import com.github.cinema.home.client.server.core.bll.SearchService;
import com.github.cinema.home.client.server.core.bll.SimilarService;
import com.github.cinema.home.client.server.core.bll.TorrentService;
import com.github.cinema.home.client.server.core.types.MediaDynamicData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final SearchService searchService;
    private final NetworkService networkService;
    private final RecommendService recommendService;
    private final DetailsService detailsService;
    private final SimilarService similarService;
    private final CollectionService collectionService;
    private final TorrentService torrentService;
    private final DownloadService downloadService;

    @Autowired
    public ClientController(SearchService searchService, NetworkService networkService, RecommendService recommendService,
                            DetailsService detailsService, SimilarService similarService, CollectionService collectionService,
                            TorrentService torrentService, DownloadService downloadService) {
        this.searchService = searchService;
        this.networkService = networkService;
        this.recommendService = recommendService;
        this.detailsService = detailsService;
        this.similarService = similarService;
        this.collectionService = collectionService;
        this.torrentService = torrentService;
        this.downloadService = downloadService;
    }

    @RequestMapping(value = "/search/movies", method = RequestMethod.POST)
    public List<MovieNfo> searchMovies(@RequestBody SearchFilter filter, @RequestParam @Min(1) int page)
            throws ServiceErrorException, InvalidArgumentException {
        return this.searchService.searchMovies(filter, page);
    }

    @RequestMapping(value = "/search/shows", method = RequestMethod.POST)
    public List<ShowNfo> searchShows(@RequestBody SearchFilter filter, @RequestParam @Min(1) int page)
            throws ServiceErrorException, InvalidArgumentException {
        return this.searchService.searchShows(filter, page);
    }

    @RequestMapping(value = "/search/movies/network/{network}", method = RequestMethod.POST)
    public List<MovieNfo> searchStreamingMovies(@PathVariable NetworkGroup network, @RequestParam @Min(1) int page)
            throws ServiceErrorException, InvalidArgumentException {
        return this.networkService.searchMovies(network, page);
    }

    @RequestMapping(value = "/search/shows/network/{network}", method = RequestMethod.POST)
    public List<ShowNfo> searchStreamingShows(@PathVariable NetworkGroup network, @RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.networkService.searchShows(network, page);
    }

    @RequestMapping(value = "/recommend/movies", method = RequestMethod.POST)
    public List<MovieNfo> recommendMovies(@RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.recommendService.recommendMovies(page);
    }

    @RequestMapping(value = "/recommend/shows", method = RequestMethod.POST)
    public List<ShowNfo> recommendShows(@RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.recommendService.recommendShows(page);
    }

    @RequestMapping(value = "/recommend/movies/{tmdbId}", method = RequestMethod.POST)
    public List<MovieNfo> recommendMoviesFor(@PathVariable TmdbId tmdbId, @RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.recommendService.recommendMoviesFor(tmdbId, page);
    }

    @RequestMapping(value = "/recommend/shows/{tmdbId}", method = RequestMethod.POST)
    public List<ShowNfo> recommendShowsFor(@PathVariable TmdbId tmdbId, @RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.recommendService.recommendShowsFor(tmdbId, page);
    }

    @RequestMapping(value = "/movie/{imdbId}", method = RequestMethod.POST)
    public MovieNfo detailMovie(@PathVariable ImdbId imdbId) {
        return this.detailsService.detailMovie(imdbId);
    }

    @RequestMapping(value = "/show/{imdbId}", method = RequestMethod.POST)
    public ShowNfo detailShow(@PathVariable ImdbId imdbId) {
        return this.detailsService.detailShow(imdbId);
    }

    @RequestMapping(value = "/similar/movies", method = RequestMethod.POST)
    public List<MovieNfo> findSimilarMovies(@RequestParam(name = "id", required = true) TmdbId tmdbId, @RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.similarService.getSimilarMoviesFor(tmdbId, page);
    }

    @RequestMapping(value = "/similar/shows", method = RequestMethod.POST)
    public List<ShowNfo> findSimilarShows(@RequestParam(name = "id", required = true) TmdbId tmdbId, @RequestParam @Min(1) int page)
            throws ServiceErrorException {
        return this.similarService.getSimilarShowsFor(tmdbId, page);
    }

    @RequestMapping(value = "/collection/movies", method = RequestMethod.POST)
    public List<MovieNfo> findCollectionMovies(@RequestParam(name = "id", required = true) TmdbId tmdbCollectionId)
            throws ServiceErrorException {
        return this.collectionService.getMoviesForCollection(tmdbCollectionId);
    }

    @RequestMapping(value = "/collection/shows", method = RequestMethod.POST)
    public List<ShowNfo> findCollectionShows(@RequestParam(name = "id", required = true) TmdbId tmdbCollectionId)
            throws ServiceErrorException {
        return this.collectionService.getShowsForCollection(tmdbCollectionId);
    }

    @RequestMapping(value = "/torrents/movie/{imdbId}", method = RequestMethod.POST)
    public MovieNfo getDownloadDetailsForMovie(@PathVariable ImdbId imdbId)
            throws ServiceErrorException, InvalidArgumentException {
        return this.torrentService.getMovieTorrents(imdbId);
    }

    @RequestMapping(value = "/torrents/show/{imdbId}", method = RequestMethod.POST)
    public ShowNfo getDownloadDetailsForShow(@PathVariable ImdbId imdbId)
            throws ServiceErrorException, InvalidArgumentException {
        return this.torrentService.getShowTorrents(imdbId);
    }

    @RequestMapping(value = "/report/{imdbId}", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void postForErrorReporting(@PathVariable ImdbId imdbId, @RequestBody String message) {
        logger.warn("----Report: " + imdbId + " message: " + message);
    }

    @RequestMapping(value = "/data/{imdbId}", method = RequestMethod.POST)
    public MediaDynamicData postForDynamicData(@PathVariable ImdbId imdbId) {
        return MediaDynamicData.builder().isFavored(true).build();
    }

    @RequestMapping(value = "/movie/release/{torrentId}", method = RequestMethod.POST)
    public void postForMovieReleaseAsync(@PathVariable String torrentId)
            throws ServiceErrorException, InvalidArgumentException {
        this.downloadService.downloadFull(torrentId);
    }

    @RequestMapping(value = "/show/release/{torrentId}", method = RequestMethod.POST)
    public void postForShowReleaseAsync(@PathVariable String torrentId,
                                        @RequestParam(required = false) Integer seasonNo,
                                        @RequestParam(required = false) Integer episodeNo)
            throws ServiceErrorException, InvalidArgumentException {
        if (seasonNo == null && episodeNo == null) {
            this.downloadService.downloadFull(torrentId);
        } else if (seasonNo != null && episodeNo == null) {
            this.downloadService.downloadSeason(torrentId, seasonNo);
        } else if (seasonNo != null) {
            this.downloadService.downloadEpisode(torrentId, seasonNo, episodeNo);
        } else {
            throw new IllegalArgumentException("Invalid download arguments! Show season number has to be given if episode number is given!");
        }
    }
}
