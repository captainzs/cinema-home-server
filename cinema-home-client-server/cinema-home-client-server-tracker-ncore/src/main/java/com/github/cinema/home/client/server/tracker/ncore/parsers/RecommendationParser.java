package com.github.cinema.home.client.server.tracker.ncore.parsers;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ReleaseNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.tech.Language;
import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;
import com.github.cinema.home.client.server.common.utils.FileSizeConverter;
import com.github.cinema.home.client.server.common.utils.LocaleDetector;
import com.github.cinema.home.client.server.torrent.title.TorrentTitleParser;
import com.github.cinema.home.client.server.torrent.title.types.EpisodeId;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecommendationParser {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationParser.class);
    private final TorrentTitleParser titleParser;
    private final LocaleDetector detector;

    @Autowired
    public RecommendationParser(TorrentTitleParser titleParser, LocaleDetector detector) {
        this.titleParser = titleParser;
        this.detector = detector;
    }

    public List<MovieNfo> parseMovies(Document html) throws InvalidArgumentException {
        Optional<Element> moviesBlock = html.getElementsByClass("fobox_all").stream()
                .filter(b -> b.getElementsByClass("fobox_fej").text().contains("Film"))
                .findFirst();
        if (!moviesBlock.isPresent()) {
            throw new InvalidArgumentException("Invalid html document given! Movies recommendation block has not been found!");
        }
        Elements allTorrentBlocks = moviesBlock.get().getElementsByAttributeValueStarting("href", "torrents.php?action=details");
        List<MovieNfo> result = new ArrayList<>();
        for (Element block : allTorrentBlocks) {
            String ncoreId = this.parseNcoreId(block);
            String torrentTitle = block.children().size() > 0 ? block.child(0).attr("title") : block.text();
            TitleNfo titleNfo = this.titleParser.parse(torrentTitle);
            try {
                MovieNfo movie = MovieNfo.builder()
                        .title(StringUtils.isEmpty(titleNfo.getMediaTitle()) ? null :
                                LocaleString.locale(titleNfo.getMediaTitle(), this.detector.detect(titleNfo.getMediaTitle())))
                        .releaseYear(titleNfo.getReleaseYearIfNotRange().orElse(null))
                        .network(titleNfo.getNetwork())
                        .genre(Boolean.TRUE.equals(titleNfo.isDocumentary()) ? Genre.DOCUMENTARY : null)
                        .poster(block.children().size() > 0 ? Image.clear(this.parseImgUrl(block.child(0))) : null)
                        .torrent(this.parseTorrent(ncoreId, block, titleNfo))
                        .build();
                result.add(movie);
            } catch (InvalidArgumentException e) {
                logger.warn(String.format("Movie with ncore-id '%s' is skipped!", ncoreId), e);
            }
        }
        return result;
    }

    public List<ShowNfo> parseShows(Document html) throws InvalidArgumentException {
        Optional<Element> showsBlock = html.getElementsByClass("fobox_all").stream()
                .filter(b -> b.getElementsByClass("fobox_fej").text().contains("Sorozat"))
                .findFirst();
        if (!showsBlock.isPresent()) {
            throw new InvalidArgumentException("Invalid html document given! Shows recommendation block has not been found!");
        }
        Elements allTorrentBlocks = showsBlock.get().getElementsByAttributeValueStarting("href", "torrents.php?action=details");
        List<ShowNfo> result = new ArrayList<>();
        for (Element block : allTorrentBlocks) {
            String ncoreId = this.parseNcoreId(block);
            String torrentTitle = block.children().size() > 0 ? block.child(0).attr("title") : block.text();

            TitleNfo titleNfo = this.titleParser.parse(torrentTitle);
            TorrentNfo torrent = this.parseTorrent(ncoreId, block, titleNfo);
            EpisodeId episodeId = titleNfo.getEpisodeId();
            boolean isComplete = titleNfo.isComplete() != null && titleNfo.isComplete() == Boolean.TRUE;
            try {
                ShowNfo show = ShowNfo.builder()
                        .title(StringUtils.isEmpty(titleNfo.getMediaTitle()) ? null :
                                LocaleString.locale(titleNfo.getMediaTitle(), this.detector.detect(titleNfo.getMediaTitle())))
                        .network(titleNfo.getNetwork())
                        .genre(Boolean.TRUE.equals(titleNfo.isDocumentary()) ? Genre.DOCUMENTARY : null)
                        .poster(block.children().size() > 0 ? Image.clear(this.parseImgUrl(block.child(0))) : null)
                        .completeTorrent(episodeId == null && isComplete ? torrent : null)
                        .seasons(episodeId != null ? episodeId.toSeasons(torrent).orElse(null) : null)
                        .unassignedTorrent(episodeId == null && !isComplete ? torrent : null)
                        .unassignedTorrent(episodeId != null && episodeId.hasHangingEpisodes() ? torrent : null)
                        .build();
                result.add(show);
            } catch (InvalidArgumentException e) {
                logger.warn(String.format("TV-Show with ncore-id '%s' is skipped!", ncoreId), e);
            }
        }
        return result;
    }

    private TorrentNfo parseTorrent(String ncoreId, Element block, TitleNfo titleNfo) throws InvalidArgumentException {
        TorrentNfo.Builder torrent = TorrentNfo.builder()
                .id(ncoreId);
        if (block.children().size() > 0) {
            Element parentDiv = block.parent();
            Elements element4Language = parentDiv.getElementsByAttributeValueContaining("src", "static.ncore.cc");
            String metaText = parentDiv.text().replaceAll("\u00a0", "");
            ReleaseNfo releaseNfoFromPage = ReleaseNfo.builder()
                    .fullSize(this.parseSize(metaText))
                    .resolutionDefinition(titleNfo.getResolution() == null ?
                            this.parseResolution(metaText) :
                            titleNfo.getResolution().byDefinition)
                    .audioLanguage(this.parseIsHunDub(element4Language) ?
                            Language.HUNGARIAN :
                            CollectionUtils.isEmpty(titleNfo.getAudioLanguages()) ? Language.ENGLISH : null)
                    .build();
            torrent
                    .release(ReleaseNfo.builder().build(titleNfo.toReleaseNfo(), releaseNfoFromPage))
                    .title(titleNfo.getTorrentTitle())
                    .seeds(this.parseSeeds(metaText))
                    .leechers(this.parseLeechers(metaText));
        } else {
            torrent
                    .release(titleNfo.toReleaseNfo())
                    .title(titleNfo.getTorrentTitle());
        }
        return torrent.build();
    }

    private String parseNcoreId(Element aElement) {
        return aElement.attr("href").split("=")[2];
    }

    private URL parseImgUrl(Element imgElement) throws InvalidArgumentException {
        String url = imgElement.attr("src");
        try {
            return new URI(url).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new InvalidArgumentException(String.format("Image url can not be parsed out of '%s'!", url));
        }
    }

    private boolean parseIsHunDub(Elements imgElements) {
        if (imgElements.size() != 1) {
            return false;
        }
        String src = imgElements.first().attr("src");
        return src.contains("hu.gif");
    }

    private ResolutionDefinition parseResolution(String divText) throws InvalidArgumentException {
        if (StringUtils.isEmpty(divText)) {
            return null;
        }
        String resolutionStr = divText.split(":")[0];
        switch (resolutionStr) {
            case "SD":
            case "DVD":
            case "DVD9":
                return ResolutionDefinition.SD;
            case "HD":
                return ResolutionDefinition.HD;
            default:
                throw new InvalidArgumentException(String.format("Resolution can not be parsed out of '%s'!", divText));
        }
    }

    private Long parseSize(String divText) {
        if (StringUtils.isEmpty(divText)) {
            return null;
        }
        String xGbSeeds = divText.split(":")[1].trim();
        String size = xGbSeeds.substring(0, xGbSeeds.lastIndexOf(' '));
        return FileSizeConverter.toBytesSize(size);
    }

    private Integer parseSeeds(String divText) {
        if (StringUtils.isEmpty(divText)) {
            return null;
        }
        String xSeedsLeechers = divText.split(":")[2].trim();
        String seedsNo = xSeedsLeechers.substring(0, xSeedsLeechers.lastIndexOf(' '));
        return Integer.parseInt(seedsNo);
    }

    private Integer parseLeechers(String divText) {
        if (StringUtils.isEmpty(divText)) {
            return null;
        }
        return Integer.parseInt(divText.split(":")[3].trim());
    }
}
