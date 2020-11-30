package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(13)
@Component
public class HardcodedParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(HardcodedParser.class);
    private static final Pattern HARDCODED = Pattern.compile("(\\W(HC))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = HARDCODED.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 hardcoded signal in torrent title! (skipped: %s)", removable));
                continue;
            }
            logger.debug(String.format("Hardcoding found: '%s'", removable));
            mutableState.properties.hardcoded(Boolean.TRUE);
            matcher = HARDCODED.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
