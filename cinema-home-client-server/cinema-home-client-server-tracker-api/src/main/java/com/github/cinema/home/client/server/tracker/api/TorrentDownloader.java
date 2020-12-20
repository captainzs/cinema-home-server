package com.github.cinema.home.client.server.tracker.api;

import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;

import java.net.URL;

public interface TorrentDownloader {
    URL getTorrentUrl(String torrentId) throws ServiceErrorException;
}
