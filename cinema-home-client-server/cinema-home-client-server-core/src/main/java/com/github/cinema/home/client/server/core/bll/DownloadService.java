package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.torrent.client.api.DownloadStarter;
import com.github.cinema.home.client.server.torrent.title.TorrentTitleParser;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import com.github.cinema.home.client.server.tracker.api.TorrentDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class DownloadService {
    private final TorrentDownloader torrentDownloader;
    private final DownloadStarter downloadStarter;
    private final TorrentTitleParser titleParser;

    @Autowired

    public DownloadService(TorrentDownloader torrentDownloader, DownloadStarter downloadStarter, TorrentTitleParser titleParser) {
        this.torrentDownloader = torrentDownloader;
        this.downloadStarter = downloadStarter;
        this.titleParser = titleParser;
    }

    public void downloadFull(String torrentId) throws ServiceErrorException, InvalidArgumentException {
        URL url = this.torrentDownloader.getTorrentUrl(torrentId);
        this.downloadStarter.downloadFiles(url, (fileName) -> true);
    }

    public void downloadSeason(String torrentId, int seasonNo) throws ServiceErrorException, InvalidArgumentException {
        URL url = this.torrentDownloader.getTorrentUrl(torrentId);
        this.downloadStarter.downloadFiles(url, fileName -> {
            TitleNfo nfo = this.titleParser.parse(fileName);
            if (nfo.getEpisodeId() == null || nfo.getEpisodeId().getSeasons() == null) {
                return false;
            }
            return nfo.getEpisodeId().getSeasons().contains(seasonNo);
        });
    }

    public void downloadEpisode(String torrentId, int seasonNo, int episodeNo) throws ServiceErrorException, InvalidArgumentException {
        URL url = this.torrentDownloader.getTorrentUrl(torrentId);
        this.downloadStarter.downloadFiles(url, fileName -> {
            TitleNfo nfo = this.titleParser.parse(fileName);
            if (nfo.getEpisodeId() == null || nfo.getEpisodeId().getSeasons() == null || nfo.getEpisodeId().getEpisodes() == null) {
                return false;
            }
            return nfo.getEpisodeId().getSeasons().contains(seasonNo) && nfo.getEpisodeId().getEpisodes().contains(episodeNo);
        });
    }
}
