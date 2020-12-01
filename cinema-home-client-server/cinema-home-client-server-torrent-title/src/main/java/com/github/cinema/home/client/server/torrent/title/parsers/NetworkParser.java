package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(15)
@Component
public class NetworkParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(NetworkParser.class);
    private static final Pattern NETWORKS = Pattern.compile(
            "(\\W(" + String.join("|", NetworkGroup.allSynonyms()) + "))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = NETWORKS.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 broadcasting-network in torrent title! (skipped: %s)", removable));
                continue;
            }
            if (matcher.start(1) < mutableState.getRemovalMinIndex()) {
                break;
            }
            logger.debug(String.format("Broadcasting network found: '%s'", removable));
            mutableState.properties.network(NetworkGroup.of(matcher.group(2)).orElse(null));
            matcher = NETWORKS.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
