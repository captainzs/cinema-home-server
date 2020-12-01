package com.github.cinema.home.client.server.torrent.title;

import com.github.cinema.home.client.server.torrent.title.parsers.ReleasePropertyParser;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TorrentTitleParser {
    private static final Logger logger = LoggerFactory.getLogger(TorrentTitleParser.class);
    private final List<ReleasePropertyParser> parsers;

    @Autowired
    public TorrentTitleParser(List<ReleasePropertyParser> parsers) {
        this.parsers = parsers;
    }

    public TitleNfo parse(String torrentTitle) {
        logger.debug(String.format("Parsing torrent title '%s'...", torrentTitle));
        ParseState mutableState = ParseState.of(torrentTitle);
        for (ReleasePropertyParser parser : this.parsers) {
            parser.parseOut(mutableState);
        }
        if (mutableState.getRemaining().length() > mutableState.getRemovalMinIndex()) {
            mutableState.remove(mutableState.getRemovalMinIndex(), mutableState.getRemaining().length() - mutableState.getRemovalMinIndex());
        }
        return mutableState.properties
                .mediaTitle(this.cleanTitle(mutableState.getRemaining()))
                .build();
    }

    private String cleanTitle(String dirtyTitle) {
        return dirtyTitle
                .replaceAll("\\.(?!$| )", " ")
                .trim();
    }
}

















