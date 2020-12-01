package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(20)
@Component
public class DocumentaryParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(DocumentaryParser.class);
    private static final Pattern DOCUMENTARY = Pattern.compile("(\\W(DOCU(?:MENTARY)?))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = DOCUMENTARY.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 documentary signal in torrent title! (skipped: %s)", removable));
                continue;
            }
            if (matcher.start(1) < mutableState.getRemovalMinIndex()) {
                break;
            }
            logger.debug(String.format("Documentary found: '%s'", removable));
            mutableState.properties.documentary(Boolean.TRUE);
            matcher = DOCUMENTARY.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
