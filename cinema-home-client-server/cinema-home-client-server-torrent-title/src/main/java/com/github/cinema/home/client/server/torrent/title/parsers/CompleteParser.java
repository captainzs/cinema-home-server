package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(3)
@Component
public class CompleteParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(CompleteParser.class);
    private static final Pattern COMPLETE = Pattern.compile("(\\W((?:The\\.)?Complete(?! [1-9]| Theatrical)(?:\\.Series|\\.Collection)?))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = COMPLETE.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            logger.debug(String.format("Complete indication found: '%s'", matcher.group(1)));
            mutableState.properties.complete(Boolean.TRUE);
            matcher = COMPLETE.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
        }
        return mutableState.properties.build();
    }
}
