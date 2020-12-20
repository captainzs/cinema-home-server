package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileStat {
    private final int bytesCompleted;
    private final int priority;
    private final boolean wanted;

    @JsonCreator
    private FileStat(
            @JsonProperty("bytesCompleted") int bytesCompleted,
            @JsonProperty("priority") int priority,
            @JsonProperty("wanted") boolean wanted) {
        this.bytesCompleted = bytesCompleted;
        this.priority = priority;
        this.wanted = wanted;
    }

    public int getBytesCompleted() {
        return this.bytesCompleted;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isWanted() {
        return this.wanted;
    }

    @Override
    public String toString() {
        return "FileStat{" +
                "bytesCompleted=" + this.bytesCompleted +
                ", priority=" + this.priority +
                ", wanted=" + this.wanted +
                '}';
    }
}
