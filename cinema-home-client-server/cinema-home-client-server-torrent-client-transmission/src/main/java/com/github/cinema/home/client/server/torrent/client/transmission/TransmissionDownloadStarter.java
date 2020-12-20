package com.github.cinema.home.client.server.torrent.client.transmission;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.torrent.client.api.DownloadStarter;
import com.github.cinema.home.client.server.torrent.client.api.FileSelector;
import com.github.cinema.home.client.server.torrent.client.transmission.api.TransmissionApi;
import com.github.cinema.home.client.server.torrent.client.transmission.types.File;
import com.github.cinema.home.client.server.torrent.client.transmission.types.FileStat;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Torrent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransmissionDownloadStarter implements DownloadStarter {
    private final TransmissionApi api;

    @Autowired
    public TransmissionDownloadStarter(TransmissionApi api) {
        this.api = api;
    }

    @Override
    public void downloadFiles(URL url, FileSelector selector) throws ServiceErrorException, InvalidArgumentException {
        Torrent addedTorrent = this.api.addTorrent(url);
        addedTorrent = this.api.getTorrentData(addedTorrent.getId());
        List<Integer> wantedIndices = new ArrayList<>();
        List<Integer> unwantedIndices = new ArrayList<>();
        for (int i = 0; i < addedTorrent.getFiles().size(); i++) {
            File file = addedTorrent.getFiles().get(i);
            FileStat stat = addedTorrent.getFileStats().get(i);
            if (selector.isFit(file.getFileName()) || (addedTorrent.isDuplicate() && stat.isWanted())) {
                wantedIndices.add(i);
            } else {
                unwantedIndices.add(i);
            }
        }
        this.api.setWantedFiles(addedTorrent.getId(), wantedIndices, unwantedIndices);
        this.api.startTorrent(addedTorrent.getId());
    }
}
