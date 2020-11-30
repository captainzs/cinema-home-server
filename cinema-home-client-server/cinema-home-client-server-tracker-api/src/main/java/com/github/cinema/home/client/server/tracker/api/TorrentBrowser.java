package com.github.cinema.home.client.server.tracker.api;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;

import java.util.List;
import java.util.Optional;

//TODO merge with mediagroupservice / mediaservice interfaces?
public interface TorrentBrowser {
    List<MovieNfo> findMovies(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException;

    List<ShowNfo> findShows(SearchFilter filter, int page) throws ServiceErrorException, InvalidArgumentException;

    List<MovieNfo> recommendMovies(int page) throws ServiceErrorException;

    List<ShowNfo> recommendShows(int page) throws ServiceErrorException;

    Optional<MovieNfo> findMovie(ImdbId id, int page) throws ServiceErrorException, InvalidArgumentException;

    Optional<ShowNfo> findShow(ImdbId id, int page) throws ServiceErrorException, InvalidArgumentException;
}
