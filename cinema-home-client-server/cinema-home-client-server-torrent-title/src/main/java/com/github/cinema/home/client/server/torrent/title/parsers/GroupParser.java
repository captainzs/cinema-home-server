package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(22)
@Component
public class GroupParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(GroupParser.class);
    private static final Pattern GROUP = Pattern.compile(".*([.-] ?(\\w+))", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = GROUP.matcher(mutableState.getRemaining());
        if (matcher.find() && matcher.start(1) >= mutableState.getRemovalMinIndex()) {
            logger.debug(String.format("Group found: '%s'", matcher.group(1)));
            mutableState.properties.group(matcher.group(2).trim());
            mutableState.remove(matcher.start(1), matcher.group(1).length());
        }
        return mutableState.properties.build();
    }
}
