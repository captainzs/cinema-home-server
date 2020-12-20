package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.tracker.api.TorrentDownloader;
import com.github.cinema.home.client.server.tracker.ncore.parsers.DetailsParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class NcoreTorrentDownloader implements TorrentDownloader {
    private final UrlMaker urlMaker;
    private final PageLoader pageLoader;
    private final DetailsParser detailsParser;

    @Autowired
    public NcoreTorrentDownloader(UrlMaker urlMaker, PageLoader pageLoader, DetailsParser detailsParser) {
        this.urlMaker = urlMaker;
        this.pageLoader = pageLoader;
        this.detailsParser = detailsParser;
    }

    @Override
    public URL getTorrentUrl(String torrentId) throws ServiceErrorException {
        URL detailsPageUrl = this.urlMaker.detailsUrl(torrentId);
        try {
            Document html = this.pageLoader.load(detailsPageUrl);
            String downloadToken = this.detailsParser.parseDownloadToken(html);
            return this.urlMaker.downloadUrl(torrentId, downloadToken);
        } catch (IOException e) {
            throw new ServiceErrorException(String.format("Ncore is not reachable! Url: %s", detailsPageUrl), e);
        } catch (InvalidArgumentException e) {
            throw new ServiceErrorException(String.format("Ncore document structure changed! Url: %s", detailsPageUrl), e);
        }
    }
}
