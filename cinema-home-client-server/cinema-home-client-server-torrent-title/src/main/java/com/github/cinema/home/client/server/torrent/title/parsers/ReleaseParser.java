package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public class ReleaseParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(ReleaseParser.class);
    private static final Pattern DATE = Pattern.compile("([.\\[\\] ]+((?:19[0-9]|20[012])[0-9][.-][01][0-9][.-][0-3][0-9]))(?:\\W|$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern YEARS = Pattern.compile("([.\\[\\] ]+((?:19[0-9]|20[012])[0-9])-?((?:19[0-9]|20[012])[0-9])?)(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        this.parseOutDate(mutableState);
        this.parseOutYears(mutableState);
        return mutableState.properties.build();
    }

    private void parseOutDate(ParseState mutableState) {
        Matcher matcher = DATE.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            String removable = matcher.group(1);
            if (i > 0) {
                logger.warn(String.format("Shall only be 1 release-date in torrent title! (skipped: %s)", removable));
                continue;
            }
            try {
                String normalizedDate = matcher.group(2).replace("-", ".");
                Date releaseDate = new SimpleDateFormat("yyyy.MM.dd").parse(normalizedDate);
                logger.debug(String.format("Release-date found: '%s'", removable));
                mutableState.properties.releaseDate(releaseDate);
                matcher = DATE.matcher(mutableState.remove(matcher.start(1), removable.length()));
            } catch (ParseException e) {
                logger.error(String.format("Release-date '%s' can not be parsed!", matcher.group(2)), e);
                break;
            }
        }
    }

    private void parseOutYears(ParseState mutableState) {
        MatchResult lastResult = null;
        Matcher matcher = YEARS.matcher(mutableState.getRemaining());
        for (int i = 0; matcher.find(); i++) {
            lastResult = matcher.toMatchResult();
        }
        if (lastResult != null) {
            String removable = lastResult.group(1);
            logger.debug(String.format("Year found: '%s'", removable));
            String from = lastResult.group(2);
            String to = lastResult.group(3);
            mutableState.properties.releaseYears(to == null ?
                    Range.just(Integer.parseInt(from)) :
                    Range.closed(Integer.parseInt(from), Integer.parseInt(to)));
            mutableState.remove(lastResult.start(1), removable.length());
        }
    }
}
