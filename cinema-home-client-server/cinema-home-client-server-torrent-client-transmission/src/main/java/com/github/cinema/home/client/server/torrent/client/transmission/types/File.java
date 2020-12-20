package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    private final int bytesCompleted;
    private final int size;
    private final String name;

    @JsonCreator
    private File(
            @JsonProperty("bytesCompleted") int bytesCompleted,
            @JsonProperty("length") int size,
            @JsonProperty("name") String name) {
        this.bytesCompleted = bytesCompleted;
        this.size = size;
        this.name = name;
    }

    public int getBytesCompleted() {
        return this.bytesCompleted;
    }

    public int getSize() {
        return this.size;
    }

    public String getName() {
        return this.name;
    }

    public Path getDirectoryPath() {
        return Paths.get(this.name).getParent();
    }

    public String getFileName() {
        return Paths.get(this.name).getFileName().toString();
    }

    @Override
    public String toString() {
        return "File{" +
                "bytesCompleted=" + this.bytesCompleted +
                ", size=" + this.size +
                ", name='" + this.name + '\'' +
                '}';
    }
}
