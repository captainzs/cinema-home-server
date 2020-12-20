package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DuplicateTorrent extends Torrent {
    @JsonCreator
    private DuplicateTorrent(
            @JsonProperty("id") Integer id,
            @JsonProperty("hashString") String hash,
            @JsonProperty("name") String name,
            @JsonProperty("fileStats") List<FileStat> fileStats,
            @JsonProperty("files") List<File> files) {
        super(id, hash, name, fileStats, files);
    }

    @Override
    public boolean isDuplicate() {
        return true;
    }
}
