package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.AudioCodec;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Order(6)
@Component
public class AudioCodecParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(AudioCodecParser.class);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        for (AudioCodec codec : AudioCodec.values()) {
            Matcher matcher = codec.pattern.matcher(mutableState.getRemaining());
            if (matcher.find()) {
                logger.debug(String.format("Audio Codec found: '%s'", matcher.group(1)));
                mutableState.properties.audioChannel(matcher.group(3)).audioCodec(codec);
                mutableState.remove(matcher.start(1), matcher.group(1).length());
            }
        }
        return mutableState.properties.build();
    }
}
