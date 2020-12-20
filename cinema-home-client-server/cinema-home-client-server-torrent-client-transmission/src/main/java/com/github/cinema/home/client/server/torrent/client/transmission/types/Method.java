package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Method {
    GET_TORRENT("torrent-get"),
    ADD_TORRENT("torrent-add"),
    SET_TORRENT("torrent-set"),
    START_TORRENT("torrent-start-now");

    public final String name;

    Method(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name;
    }
}
