package com.github.cinema.home.client.server.common;

import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;

//TODO shall be reevaluated after TvdbApi, 3 apis can be shadowed by this interface?
public interface MediaExtenderService {
    MovieNfo extend(MovieNfo movie) throws ServiceErrorException;

    MovieNfo extend(MovieNfo movie, ExtentDecider breakPoint) throws ServiceErrorException;

    ShowNfo extend(ShowNfo show) throws ServiceErrorException;

    ShowNfo extend(ShowNfo show, ExtentDecider breakPoint) throws ServiceErrorException;
}
