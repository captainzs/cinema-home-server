package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(10)
@Component
public class DynamicRangeParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(DynamicRangeParser.class);
    private static final Pattern STANDARDS = Pattern.compile("([. ](HDR|DNR|HDR10(?:\\+|Plus)?|Dolby.?Vision(?:.[DS]L)?))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = STANDARDS.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            logger.debug(String.format("Dynamic Range found: '%s'", matcher.group(1)));
            mutableState.properties.hdr(true);
            matcher = STANDARDS.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
        }
        return mutableState.properties.build();
    }
}
