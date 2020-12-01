package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.common.types.tech.Language;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(17)
@Component
public class AudioLanguageParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(AudioLanguageParser.class);
    private static final Pattern LANGUAGES = Pattern.compile(
            "(\\W(?:[2-9]x)?(" + String.join("|", Language.allSynonyms()) + ")(?:NARRATOR|DUB)?(?:.?([257].[01]))?)(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = LANGUAGES.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            if (matcher.start(1) < mutableState.getRemovalMinIndex()) {
                break;
            }
            logger.debug(String.format("Audio Language found: '%s'", matcher.group(1)));
            mutableState.properties.audioChannel(matcher.group(3)).audioLanguage(Language.of(matcher.group(2)).orElse(null));
            matcher = LANGUAGES.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
        }
        return mutableState.properties.build();
    }
}
