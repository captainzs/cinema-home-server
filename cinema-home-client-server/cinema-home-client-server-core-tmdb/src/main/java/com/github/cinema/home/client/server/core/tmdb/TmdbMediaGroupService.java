package com.github.cinema.home.client.server.core.tmdb;

import com.github.cinema.home.client.server.common.MediaGroupService;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.api.BelongingApi;
import com.github.cinema.home.client.server.core.tmdb.api.DiscoverApi;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbCompany;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.config.NetworkApiMappings;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TmdbMediaGroupService implements MediaGroupService {
    private final BelongingApi belongingApi;
    private final DiscoverApi discoverApi;

    @Autowired
    public TmdbMediaGroupService(BelongingApi apiService, DiscoverApi discoverApi) {
        this.belongingApi = apiService;
        this.discoverApi = discoverApi;
    }

    @Override
    public List<MovieNfo> findSimilarMoviesFor(TmdbId id, int page) {
        List<TmdbMovie> results = this.belongingApi.requestSimilarMoviesFor(id, page).getResults();
        return results.stream()
                .map(TmdbMovie::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowNfo> findSimilarShowsFor(TmdbId id, int page) {
        List<TmdbShow> results = this.belongingApi.requestSimilarShowsFor(id, page).getResults();
        return results.stream()
                .map(TmdbShow::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieNfo> findRecommendedMoviesFor(TmdbId id, int page) {
        List<TmdbMovie> results = this.belongingApi.requestRecommendedMoviesFor(id, page).getResults();
        return results.stream()
                .map(TmdbMovie::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowNfo> findRecommendedShowsFor(TmdbId id, int page) {
        List<TmdbShow> results = this.belongingApi.requestRecommendedShowsFor(id, page).getResults();
        return results.stream()
                .map(TmdbShow::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieNfo> findMoviesForCollection(TmdbId collectionId) {
        List<TmdbMovie> results = this.belongingApi.requestMovieCollection(collectionId).getParts();
        return results.stream()
                .map(TmdbMovie::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowNfo> findShowsForCollection(TmdbId collectionId) {
        List<TmdbShow> results = this.belongingApi.requestShowCollection(collectionId).getParts();
        return results.stream()
                .map(TmdbShow::toInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowNfo> findShowsOf(NetworkGroup network, int page) {
        Set<Integer> networkIds = NetworkApiMappings.of(network).stream().map(TmdbCompany::getId).collect(Collectors.toSet());
        if (networkIds.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<TmdbShow> shows = this.discoverApi.requestStreamedShows(networkIds, page).getResults();
        return shows.stream()
                .map(TmdbShow::toInfo)
                .collect(Collectors.toList());
    }
}
