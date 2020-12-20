package com.github.cinema.home.client.server.torrent.client.api;

@FunctionalInterface
public interface FileSelector {
    boolean isFit(String fileName);
}
