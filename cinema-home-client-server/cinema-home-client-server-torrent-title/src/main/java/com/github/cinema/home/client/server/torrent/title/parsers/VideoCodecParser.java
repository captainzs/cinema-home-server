package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.VideoCodec;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(5)
@Component
public class VideoCodecParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(VideoCodecParser.class);
    private static final Pattern CODECS = Pattern.compile(
            "(\\W(" + String.join("|", VideoCodec.allSynonyms()) + "))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = CODECS.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            logger.debug(String.format("Video-codec found: '%s'", removable));
            mutableState.properties.videoCodec(VideoCodec.of(matcher.group(2)).orElse(null));
            matcher = CODECS.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
