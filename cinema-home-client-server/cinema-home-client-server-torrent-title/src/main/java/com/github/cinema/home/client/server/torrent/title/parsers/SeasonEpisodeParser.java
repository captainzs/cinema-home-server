package com.github.cinema.home.client.server.torrent.title.parsers;


import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.EpisodeId;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(2)
@Component
public class SeasonEpisodeParser implements ReleasePropertyParser {
    private static final Logger logger = LoggerFactory.getLogger(SeasonEpisodeParser.class);
    private static final Pattern COMPLETE_SEASON = Pattern.compile("(\\W+(?:The[ .])?Complete[ .]([1-9])(?:st|nd|rd|th)[. ]Season)(?:\\W|$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern SEASONS_WO_EPISODE = Pattern.compile("(\\W+(?:(?:The.)?Complete.)?S([0-9]{1,2})(?:-S([0-9]{1,2}))?)(?:\\W|$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern EPISODES_WO_SEASON = Pattern.compile("(\\W+E([0-9]{1,4})(?:-E([0-9]{1,4}))?)(?:\\W|$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern SEASON_AND_EPISODE = Pattern.compile("(\\W+S([0-9]{1,2})E([0-9]{1,4}))(?:\\W|$)", Pattern.CASE_INSENSITIVE);

    @Override
    public TitleNfo parseOut(ParseState mutableState) {
        Matcher matcher = COMPLETE_SEASON.matcher(mutableState.getRemaining());
        if (matcher.find()) {
            logger.debug(String.format("Season found: '%s'", matcher.group(1)));
            mutableState.properties.episodeId(EpisodeId.season(Integer.parseInt(matcher.group(2))));
            mutableState.remove(matcher.start(1), matcher.group(1).length());
        }
        matcher = SEASONS_WO_EPISODE.matcher(mutableState.getRemaining());
        if (matcher.find()) {
            logger.debug(String.format("Season(s) found: '%s'", matcher.group(1)));
            String from = matcher.group(2);
            String to = matcher.group(3);
            if (to == null) {
                mutableState.properties.episodeId(EpisodeId.season(Integer.parseInt(from)));
            } else {
                mutableState.properties.episodeId(EpisodeId.seasons(Integer.parseInt(from), Integer.parseInt(to)));
            }
            mutableState.remove(matcher.start(1), matcher.group(1).length());
        }
        matcher = EPISODES_WO_SEASON.matcher(mutableState.getRemaining());
        if (matcher.find()) {
            logger.debug(String.format("Episode(s) found: '%s'", matcher.group(1)));
            String from = matcher.group(2);
            String to = matcher.group(3);
            if (to == null) {
                mutableState.properties.episodeId(EpisodeId.episode(Integer.parseInt(from)));
            } else {
                mutableState.properties.episodeId(EpisodeId.episodes(Integer.parseInt(from), Integer.parseInt(to)));
            }
            mutableState.remove(matcher.start(1), matcher.group(1).length());
        }
        matcher = SEASON_AND_EPISODE.matcher(mutableState.getRemaining());
        if (matcher.find()) {
            logger.debug(String.format("Season-Episode found: '%s'", matcher.group(1)));
            mutableState.properties.episodeId(EpisodeId.episodes(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(3))));
            mutableState.remove(matcher.start(1), matcher.group(1).length());
        }
        return mutableState.properties.build();
    }
}
