package com.github.cinema.home.client.server.torrent.client.transmission.config;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Component
@ConfigurationProperties(prefix = "transmission")
public class TransmissionProperties {
    private URI rpcUrl;

    public synchronized URI getRpcUrl() {
        return this.rpcUrl;
    }

    public synchronized void setRpcUrl(String rpcUrl) {
        try {
            this.rpcUrl = new URL(rpcUrl).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new InvalidConfigurationException(String.format("Transmission client service host uri-address is invalid! ('%s')", this.rpcUrl));
        }
    }
}
