package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.ColorCodec;
import com.github.cinema.home.client.server.common.types.tech.Scanning;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(11)
@Component
public class ColorCodecParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(ColorCodecParser.class);
    private static final Pattern CODECS = Pattern.compile(
            "(\\W(" + String.join("|", ColorCodec.allSynonyms()) + "))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = CODECS.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 color-codec in torrent title! (skipped: %s)", removable));
                continue;
            }
            ColorCodec codec = ColorCodec.of(matcher.group(2)).orElse(null);
            logger.debug(String.format("Color-codec found: '%s'", removable));
            if (codec == ColorCodec.NTSC || codec == ColorCodec.PAL || codec == ColorCodec.SECAM) {
                logger.debug(String.format("Scanning type set to interlaced because of '%s' color-codec!", codec));
                mutableState.properties.scanning(Scanning.INTERLACED);
            }
            mutableState.properties.colorCodec(codec);
            matcher = CODECS.matcher(mutableState.remove(matcher.start(1), removable.length()));
        }
        return mutableState.properties.build();
    }
}
