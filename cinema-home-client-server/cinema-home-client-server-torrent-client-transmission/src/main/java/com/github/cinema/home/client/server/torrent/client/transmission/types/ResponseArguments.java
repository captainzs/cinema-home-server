package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseArguments {
    private final DuplicateTorrent duplicateTorrent;
    private final Torrent addedTorrent;
    private final List<Torrent> torrents;

    @JsonCreator
    private ResponseArguments(
            @JsonProperty("torrent-duplicate") DuplicateTorrent duplicateTorrent,
            @JsonProperty("torrent-added") Torrent addedTorrent,
            @JsonProperty("torrent") List<Torrent> torrents) {
        this.duplicateTorrent = duplicateTorrent;
        this.addedTorrent = addedTorrent;
        this.torrents = torrents;
    }

    public Torrent getAddedOrDuplicate() {
        return this.duplicateTorrent == null ? this.addedTorrent : this.duplicateTorrent;
    }

    public List<Torrent> getTorrents() {
        return this.torrents;
    }

    @Override
    public String toString() {
        return "ResponseArguments{" +
                "duplicateTorrent=" + this.duplicateTorrent +
                ", addedTorrent=" + this.addedTorrent +
                ", torrents=" + this.torrents +
                '}';
    }
}
