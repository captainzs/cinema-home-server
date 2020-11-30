package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.Resolution;
import com.github.cinema.home.client.server.common.types.tech.Scanning;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(4)
@Component
public class ResolutionParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(ResolutionParser.class);
    private static final Pattern RES_SCAN = Pattern.compile("(\\W+([1-9][0-9]{2,3})([pi]))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = RES_SCAN.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 resolution in torrent title: '%s'! (skipped: %s)", mutableState.getRemaining(), removable));
                continue;
            }
            logger.debug(String.format("Resolution found: '%s'", removable));
            mutableState.properties.resolution(Resolution.of(matcher.group(2)).orElse(null))
                    .scanning(Scanning.of(matcher.group(3)).orElse(null));
            matcher = RES_SCAN.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
