package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(12)
@Component
public class CDCountParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(CDCountParser.class);
    private static final Pattern CD_COUNT = Pattern.compile("(\\W([2-4])CD)(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = CD_COUNT.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 CD-count indication in torrent title! (skipped: %s)", removable));
                continue;
            }
            logger.debug(String.format("CD-count found: '%s'", removable));
            matcher = CD_COUNT.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
