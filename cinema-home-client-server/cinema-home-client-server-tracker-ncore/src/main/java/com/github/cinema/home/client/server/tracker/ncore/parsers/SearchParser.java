package com.github.cinema.home.client.server.tracker.ncore.parsers;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.types.media.Image;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.LocaleString;
import com.github.cinema.home.client.server.common.types.media.MediaType;
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
import com.github.cinema.home.client.server.tracker.ncore.conf.ApiMappings;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SearchParser {
    private static final Logger logger = LoggerFactory.getLogger(SearchParser.class);
    private static final int MAX_VIEWS_SYMBOL = 6;

    private final TorrentTitleParser titleParser;
    private final LocaleDetector detector;

    @Autowired
    public SearchParser(TorrentTitleParser titleParser, LocaleDetector detector) {
        this.titleParser = titleParser;
        this.detector = detector;
    }

    public List<MovieNfo> parseMovies(Document html) {
        List<MovieNfo> result = new ArrayList<>();
        Elements allTorrentBlocks = html.getElementsByClass("box_torrent");
        for (Element block : allTorrentBlocks) {
            Elements titleLinkEs = block.getElementsByAttributeValueStarting("onclick", "torrent(");
            Elements imdbAndRatingBlocks = block.getElementsByClass("infolink");
            String ncoreId = null;
            try {
                ncoreId = this.parseNcoreId(titleLinkEs);
                MediaType type = this.parseType(block.getElementsByAttributeValueStarting("href", "/torrents.php?tipus="));
                if (type != MediaType.MOVIE) {
                    logger.warn("One of the records is NOT a movie when parsing movies from ncore! The url was possibly wrong!");
                    continue;
                }
                TitleNfo titleNfo = this.titleParser.parse(this.parseTorrentTitle(titleLinkEs));
                String title = this.parseSubTitle(block.select("span[title]"));
                MovieNfo movie = MovieNfo.builder()
                        .imdbId(this.parseImdbId(imdbAndRatingBlocks))
                        .title(StringUtils.isEmpty(title) ? null : LocaleString.locale(title, this.detector.detect(title)))
                        .title(StringUtils.isEmpty(titleNfo.getMediaTitle()) ? null :
                                LocaleString.locale(titleNfo.getMediaTitle(), this.detector.detect(titleNfo.getMediaTitle())))
                        .rating(this.parseImdbRating(imdbAndRatingBlocks))
                        .releaseYear(titleNfo.getReleaseYearIfNotRange().orElse(null))
                        .network(titleNfo.getNetwork())
                        .genre(Boolean.TRUE.equals(titleNfo.isDocumentary()) ? Genre.DOCUMENTARY : null)
                        .poster(this.parseImgUrl(block.getElementsByAttributeValueStarting("onmouseover", "mutat(")))
                        .torrent(this.parseTorrent(ncoreId, block, titleNfo))
                        .build();
                result.add(movie);
            } catch (InvalidArgumentException e) {
                logger.warn(String.format("Movie with ncore-id '%s' is skipped!", ncoreId), e);
            }
        }
        return result;
    }

    public List<ShowNfo> parseShows(Document html) {
        List<ShowNfo> result = new ArrayList<>();
        Elements allTorrentBlocks = html.getElementsByClass("box_torrent");
        for (Element block : allTorrentBlocks) {
            Elements titleLinkEs = block.getElementsByAttributeValueStarting("onclick", "torrent(");
            Elements imdbAndRatingBlocks = block.getElementsByClass("infolink");
            String ncoreId = null;
            try {
                ncoreId = this.parseNcoreId(titleLinkEs);
                MediaType type = this.parseType(block.getElementsByAttributeValueStarting("href", "/torrents.php?tipus="));
                if (type != MediaType.SHOW) {
                    logger.warn("One of the records is NOT a tv-show when parsing tv-shows from ncore! The url was possibly wrong!");
                    continue;
                }

                TitleNfo titleNfo = this.titleParser.parse(this.parseTorrentTitle(titleLinkEs));
                TorrentNfo torrent = this.parseTorrent(ncoreId, block, titleNfo);
                EpisodeId episodeId = titleNfo.getEpisodeId();
                String title = this.parseSubTitle(block.select("span[title]"));
                boolean isComplete = titleNfo.isComplete() != null && titleNfo.isComplete() == Boolean.TRUE;
                ShowNfo show = ShowNfo.builder()
                        .imdbId(this.parseImdbId(imdbAndRatingBlocks))
                        .title(StringUtils.isEmpty(title) ? null : LocaleString.locale(title, this.detector.detect(title)))
                        .title(StringUtils.isEmpty(titleNfo.getMediaTitle()) ? null :
                                LocaleString.locale(titleNfo.getMediaTitle(), this.detector.detect(titleNfo.getMediaTitle())))
                        .rating(this.parseImdbRating(imdbAndRatingBlocks))
                        .network(titleNfo.getNetwork())
                        .genre(Boolean.TRUE.equals(titleNfo.isDocumentary()) ? Genre.DOCUMENTARY : null)
                        .poster(this.parseImgUrl(block.getElementsByAttributeValueStarting("onmouseover", "mutat(")))
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
        Elements lang2TypeEs = block.getElementsByAttributeValueStarting("href", "/torrents.php?tipus=");
        ReleaseNfo releaseNfoFromPage = ReleaseNfo.builder()
                .fullSize(this.parseSize(block.getElementsByClass("box_meret2")))
                .resolutionDefinition(titleNfo.getResolution() == null ?
                        this.parseResolution(lang2TypeEs) :
                        titleNfo.getResolution().byDefinition)
                .audioLanguage(this.parseIsHunDub(lang2TypeEs) ?
                        Language.HUNGARIAN :
                        CollectionUtils.isEmpty(titleNfo.getAudioLanguages()) ? Language.ENGLISH : null)
                .build();
        return TorrentNfo.builder()
                .release(ReleaseNfo.builder().build(titleNfo.toReleaseNfo(), releaseNfoFromPage))
                .id(ncoreId)
                .title(titleNfo.getTorrentTitle())
                .uploadDate(this.parseUploadDate(block.getElementsByClass("box_feltoltve2")))
                .fameRate(this.parseFameRate(block.getElementsByClass("box_d2")))
                .seeds(this.parsePeers(block.getElementsByClass("box_s2").first().getElementsByTag("a")))
                .leechers(this.parsePeers(block.getElementsByClass("box_l2").first().getElementsByTag("a")))
                .uploader(this.parseUploader(block.getElementsByClass("box_feltolto2").first().getElementsByTag("span")))
                .build();
    }

    private MediaType parseType(Elements aElements) throws InvalidArgumentException {
        String lang2Type = aElements.first().attr("href").split("=")[1];
        return ApiMappings.toMediaType(lang2Type)
                .orElseThrow(() -> new InvalidArgumentException(String.format("Media-Type can not be mapped to '%s'!", lang2Type)));
    }

    private boolean parseIsHunDub(Elements aElements) throws InvalidArgumentException {
        if (aElements.size() != 1) {
            throw new InvalidArgumentException("Audio Dub can not be parsed, no unique block exists!");
        }
        String lang2Type = aElements.first().attr("href").split("=")[1];
        return lang2Type.contains("hun");
    }

    private ResolutionDefinition parseResolution(Elements aElements) throws InvalidArgumentException {
        if (aElements.size() != 1) {
            throw new InvalidArgumentException("Resolution can not be parsed, no unique block exists!");
        }
        String lang2Type = aElements.first().attr("href").split("=")[1];
        if (lang2Type.contains("dvd")) {
            return ResolutionDefinition.SD;
        } else if (lang2Type.contains("xvid")) {
            return ResolutionDefinition.SD;
        } else if (lang2Type.contains("hd")) {
            return ResolutionDefinition.HD;
        } else {
            throw new InvalidArgumentException(String.format("Resolution can not be parsed out of '%s' language-2-type attribute!", lang2Type));
        }
    }

    private String parseNcoreId(Elements aElements) throws InvalidArgumentException {
        if (aElements.size() != 1) {
            throw new InvalidArgumentException("Ncore-Id can not be parsed, no unique block exists!");
        }
        return aElements.first().attr("href").split("=")[2];
    }

    private String parseTorrentTitle(Elements aElements) throws InvalidArgumentException {
        if (aElements.size() != 1) {
            throw new InvalidArgumentException("Torrent-title can not be parsed, no unique block exists!");
        }
        return aElements.first().attr("title");
    }

    private Image parseImgUrl(Elements imgElements) throws InvalidArgumentException {
        if (imgElements.size() == 0) {
            return null;
        } else if (imgElements.size() == 1) {
            String url = imgElements.first().attr("onmouseover").split("'")[1];
            try {
                return Image.clear(new URI(url).toURL());
            } catch (MalformedURLException | URISyntaxException e) {
                throw new InvalidArgumentException(String.format("Image URL can not be created of '%s'!", url));
            }
        } else {
            throw new InvalidArgumentException("Image url can not be parsed, no unique block exists!");
        }
    }

    private String parseSubTitle(Elements spanElements) throws InvalidArgumentException {
        if (spanElements.size() == 0) {
            return null;
        } else if (spanElements.size() > 1) {
            throw new InvalidArgumentException("Sub-title can not be parsed, no unique block exists!");
        }
        return spanElements.first().attr("title");
    }

    private ImdbId parseImdbId(Elements aElements) {
        if (aElements.size() == 0) {
            return null;
        }
        String href = aElements.first().attr("href");
        return ImdbId.of(href.substring(href.indexOf("/tt") + 1, href.lastIndexOf('/')));
    }

    private Double parseImdbRating(Elements aElements) {
        if (aElements.size() == 0) {
            return null;
        }
        String text = aElements.text();
        String rateStr = text.replace("[", "").replace("]", "").replace("imdb:", "").trim();
        double rate = Double.parseDouble(rateStr);
        return rate > 0.0 ? rate : null;
    }

    private Date parseUploadDate(Elements divElements) throws InvalidArgumentException {
        if (divElements.size() != 1) {
            throw new InvalidArgumentException("Upload-Date can not be parsed, no unique block exists!");
        }
        String text = divElements.first().text();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(text);
        } catch (ParseException e) {
            throw new InvalidArgumentException(String.format("Upload date can not be created of '%s'!", text));
        }
    }

    private long parseSize(Elements divElements) throws InvalidArgumentException {
        if (divElements.size() != 1) {
            throw new InvalidArgumentException("Size can not be parsed, no unique block exists!");
        }
        return FileSizeConverter.toBytesSize(divElements.first().text());
    }

    private double parseFameRate(Elements divElements) throws InvalidArgumentException {
        if (divElements.size() != 1) {
            throw new InvalidArgumentException("Fame rate can not be parsed, no unique block exists!");
        }
        return ((double) divElements.first().text().length() / MAX_VIEWS_SYMBOL) * 100;
    }

    private int parsePeers(Elements aElements) throws InvalidArgumentException {
        if (aElements.size() != 1) {
            throw new InvalidArgumentException("Peers can not be parsed, no unique block exists!");
        }
        return Integer.parseInt(aElements.first().text());
    }

    private String parseUploader(Elements elements) throws InvalidArgumentException {
        if (elements.size() != 1) {
            throw new InvalidArgumentException("Uploader can not be parsed, no unique block exists!");
        }
        return elements.first().text();
    }
}
