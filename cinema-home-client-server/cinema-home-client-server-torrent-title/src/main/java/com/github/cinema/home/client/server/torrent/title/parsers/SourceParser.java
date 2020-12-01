package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.Source;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Order(7)
@Component
public class SourceParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(SourceParser.class);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        for (Source src : Source.values()) {
            Matcher matcher = src.pattern.matcher(mutableState.getRemaining());
            while (matcher.find()) {
                logger.debug(String.format("Source found: '%s'", matcher.group(1)));
                mutableState.properties.source(src);
                matcher = src.pattern.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
            }
        }
        return mutableState.properties.build();
    }
}
