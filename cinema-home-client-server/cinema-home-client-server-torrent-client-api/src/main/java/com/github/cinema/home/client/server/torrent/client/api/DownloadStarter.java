package com.github.cinema.home.client.server.torrent.client.api;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;

import java.net.URL;

public interface DownloadStarter {
    void downloadFiles(URL url, FileSelector selector) throws ServiceErrorException, InvalidArgumentException;
}
