package com.github.cinema.home.client.server.common;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;

import java.util.List;

//TODO shall be reevaluated after TvdbApi, 3 apis can be shadowed by this interface?
public interface MediaGroupService {
    List<MovieNfo> findSimilarMoviesFor(TmdbId id, int page) throws ServiceErrorException;

    List<ShowNfo> findSimilarShowsFor(TmdbId id, int page) throws ServiceErrorException;

    List<MovieNfo> findRecommendedMoviesFor(TmdbId id, int page) throws ServiceErrorException;

    List<ShowNfo> findRecommendedShowsFor(TmdbId id, int page) throws ServiceErrorException;

    List<MovieNfo> findMoviesForCollection(TmdbId collectionId) throws ServiceErrorException;

    List<ShowNfo> findShowsForCollection(TmdbId collectionId) throws ServiceErrorException;

    List<ShowNfo> findShowsOf(NetworkGroup network, int page) throws ServiceErrorException;
}
