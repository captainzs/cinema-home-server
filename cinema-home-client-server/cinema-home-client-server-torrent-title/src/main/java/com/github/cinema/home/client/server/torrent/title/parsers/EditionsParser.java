package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.Edition;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Order(9)
@Component
public class EditionsParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(EditionsParser.class);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        for (Edition edition : Edition.values()) {
            Matcher matcher = edition.pattern.matcher(mutableState.getRemaining());
            if (matcher.find()) {
                logger.debug(String.format("Edition found: '%s'", matcher.group(1)));
                mutableState.properties.edition(edition);
                mutableState.remove(matcher.start(1), matcher.group(1).length());
            }
        }
        return mutableState.properties.build();
    }
}
