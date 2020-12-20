package com.github.cinema.home.client.server.torrent.client.transmission.config;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ConfigurationProperties(prefix = "transmission")
public class TransmissionProperties {
    private URI rpcUrl;
    private Path downloadDirectory;

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

    public synchronized Path getDownloadDirectory() {
        return this.downloadDirectory;
    }

    public synchronized void setDownloadDirectory(String downloadDirectory) {
        Path downloadDirectoryPath = Paths.get(downloadDirectory);
        if (!Files.isDirectory(downloadDirectoryPath)) {
            throw new InvalidConfigurationException(String.format("Configured download directory is not a directory! ('%s')", downloadDirectory));
        }
        this.downloadDirectory = downloadDirectoryPath;
    }
}
