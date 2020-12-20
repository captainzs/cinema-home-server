package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Torrent {
    private final Integer id;
    private final String hash;
    private final String name;
    private final List<FileStat> fileStats;
    private final List<File> files;

    @JsonCreator
    protected Torrent(
            @JsonProperty("id") Integer id,
            @JsonProperty("hashString") String hash,
            @JsonProperty("name") String name,
            @JsonProperty("fileStats") List<FileStat> fileStats,
            @JsonProperty("files") List<File> files) {
        this.id = id;
        this.hash = hash;
        this.name = name;
        this.fileStats = fileStats;
        this.files = files;
    }

    public boolean isDuplicate() {
        return false;
    }

    public Integer getId() {
        return this.id;
    }

    public String getHash() {
        return this.hash;
    }

    public String getName() {
        return this.name;
    }

    public List<FileStat> getFileStats() {
        return this.fileStats;
    }

    public List<File> getFiles() {
        return this.files;
    }

    @Override
    public String toString() {
        return "Torrent{" +
                "id=" + this.id +
                ", hash='" + this.hash + '\'' +
                ", name='" + this.name + '\'' +
                ", fileStats=" + this.fileStats +
                ", files=" + this.files +
                '}';
    }
}
