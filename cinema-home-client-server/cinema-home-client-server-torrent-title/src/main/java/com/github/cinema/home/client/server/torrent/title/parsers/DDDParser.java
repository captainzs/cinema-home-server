package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(16)
@Component
public class DDDParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(DDDParser.class);
    private static final Pattern DDD = Pattern.compile("(\\W(3D|ANAGLYPH|Half[.-]?(?:SBS|OU)))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = DDD.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            if (matcher.start(1) < mutableState.getRemovalMinIndex()) {
                break;
            }
            logger.debug(String.format("3D found: '%s'", matcher.group(1)));
            mutableState.properties.ddd(Boolean.TRUE);
            matcher = DDD.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
        }
        return mutableState.properties.build();
    }
}
