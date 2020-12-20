package com.github.cinema.home.client.server.torrent.client.transmission.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransmissionAccountProperties {
    private final String username;
    private final String password;

    @Autowired
    public TransmissionAccountProperties() {
        this.username = System.getenv("TRANSMISSION_USERNAME");
        this.password = System.getenv("TRANSMISSION_PASSWORD");
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
