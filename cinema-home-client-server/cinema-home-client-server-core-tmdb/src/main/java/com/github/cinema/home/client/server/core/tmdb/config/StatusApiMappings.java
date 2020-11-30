package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import com.github.cinema.home.client.server.common.types.media.MovieStatus;
import com.github.cinema.home.client.server.common.types.media.ShowStatus;
import com.google.common.collect.ImmutableMap;
import org.springframework.util.StringUtils;

import java.util.Map;

public class StatusApiMappings {
    private static final ImmutableMap<MovieStatus, String> MOVIE_STATUSES = ImmutableMap.<MovieStatus, String>builder()
            .put(MovieStatus.Planned, "Planned")
            .put(MovieStatus.Released, "Released")
            .put(MovieStatus.InProduction, "In Production")
            .put(MovieStatus.PostProduction, "Post Production")
            .build();

    public static MovieStatus toMovieStatus(String tmdbStatus) {
        if (StringUtils.isEmpty(tmdbStatus)) {
            return null;
        }
        return MOVIE_STATUSES.entrySet().stream()
                .filter(e -> e.getValue().equals(tmdbStatus))
                .map(Map.Entry::getKey).findFirst()
                .orElseThrow(() ->
                        new InvalidConfigurationException(String.format("No mapping exists for tmdb-movie-status: '%s'", tmdbStatus)));
    }

    private static final ImmutableMap<ShowStatus, String> SHOW_STATUSES = ImmutableMap.<ShowStatus, String>builder()
            .put(ShowStatus.Returning, "Returning Series")
            .put(ShowStatus.Planned, "Planned")
            .put(ShowStatus.InProduction, "In Production")
            .put(ShowStatus.Ended, "Ended")
            .put(ShowStatus.Cancelled, "Canceled")
            .put(ShowStatus.Pilot, "Pilot")
            .build();

    public static ShowStatus toShowStatus(String tmdbStatus) {
        if (StringUtils.isEmpty(tmdbStatus)) {
            return null;
        }
        return SHOW_STATUSES.entrySet().stream()
                .filter(e -> e.getValue().equals(tmdbStatus))
                .map(Map.Entry::getKey).findFirst()
                .orElseThrow(() ->
                        new InvalidConfigurationException(String.format("No mapping exists for tmdb-show-status: '%s'", tmdbStatus)));
    }
}
