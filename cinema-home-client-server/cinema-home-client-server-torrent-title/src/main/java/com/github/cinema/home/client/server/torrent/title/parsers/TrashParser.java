package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(23)
@Component
public class TrashParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(TrashParser.class);
    private static final Pattern TRASH = Pattern.compile("(\\W(NARRATOR|DL|DUAL|MULTI|ML|MULTISUBS|SUBBED|DUBBED|" +
                    "PROPER|LIMITED|INTERNAL|STV|REPACK|RERIP|REMASTERED|NUKED|DUPE|READ|READ.?NFO|NFO|OLVASS|BLUR|R[56]|" +
                    "v[1-9].[0-9]|CUSTOM|UNRATED(?:.CUT)?|(?:R.)?RATED|RETAIL|FESTIVAL|FIX(?:ED)?|35mm|UHD|" +
                    "OPEN.MATTE))(?:\\W|$)",
            Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = TRASH.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            if (matcher.start(1) < mutableState.getRemovalMinIndex()) {
                break;
            }
            logger.debug(String.format("Trash found: '%s'", matcher.group(1)));
            matcher = TRASH.matcher(mutableState.remove(matcher.start(1), matcher.group(1).length()));
        }
        return mutableState.properties.build();
    }
}
