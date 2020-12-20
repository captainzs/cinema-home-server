package com.github.cinema.home.client.server.torrent.client.transmission.api;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.torrent.client.transmission.client.TransmissionClient;
import com.github.cinema.home.client.server.torrent.client.transmission.config.TransmissionProperties;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Method;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Request;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Response;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Torrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransmissionApi {
    private final TransmissionClient client;
    private final TransmissionProperties properties;

    @Autowired
    public TransmissionApi(TransmissionClient client, TransmissionProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    public Torrent addTorrent(URL url) throws ServiceErrorException, InvalidArgumentException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("paused", true);
        arguments.put("download-dir", this.properties.getDownloadDirectory().toAbsolutePath().toString());
        arguments.put("filename", url.toString());
        Response response = this.client.doRequest(Request.builder()
                .method(Method.ADD_TORRENT)
                .arguments(arguments)
                .build());
        if (!response.isSuccess()) {
            throw new InvalidArgumentException(String.format("Adding a torrent failed! Response: '%s'", response));
        }
        return response.getArguments().getAddedOrDuplicate();
    }

    public Torrent getTorrentData(int id) throws ServiceErrorException, InvalidArgumentException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("ids", id);
        arguments.put("fields", Arrays.asList("id", "files", "fileStats"));
        Response response = this.client.doRequest(Request.builder()
                .method(Method.GET_TORRENT)
                .arguments(arguments)
                .build());
        if (!response.isSuccess() || response.getArguments().getTorrents().isEmpty()) {
            throw new InvalidArgumentException(String.format("Getting a torrent failed! Response: '%s'", response));
        }
        return response.getArguments().getTorrents().get(0);
    }

    public void setWantedFiles(int id, List<Integer> wantedIndices, List<Integer> unwantedIndices) throws ServiceErrorException, InvalidArgumentException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("ids", id);
        if (!wantedIndices.isEmpty()) {
            arguments.put("files-wanted", wantedIndices);
        }
        if (!unwantedIndices.isEmpty()) {
            arguments.put("files-unwanted", unwantedIndices);
        }
        Response response = this.client.doRequest(Request.builder()
                .method(Method.SET_TORRENT)
                .arguments(arguments)
                .build());
        if (!response.isSuccess()) {
            throw new InvalidArgumentException(String.format("Setting a torrent failed! Response: '%s'", response));
        }
    }

    public void startTorrent(int id) throws ServiceErrorException, InvalidArgumentException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("ids", id);
        Response response = this.client.doRequest(Request.builder()
                .method(Method.START_TORRENT)
                .arguments(arguments)
                .build());
        if (!response.isSuccess()) {
            throw new InvalidArgumentException(String.format("Starting a torrent failed! Response: '%s'", response));
        }
    }
}
