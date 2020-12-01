package com.github.cinema.home.client.server.tracker.ncore.conf;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import com.github.cinema.home.client.server.common.types.SortBy;
import com.github.cinema.home.client.server.common.types.SortOrder;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.types.media.MediaType;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ApiMappings {
    private static final ImmutableMap<Genre, String> GENRES = ImmutableMap.<Genre, String>builder()
            .put(Genre.ACTION, "akcio")
            .put(Genre.ADVENTURE, "kaland")
            .put(Genre.ANIMATION, "animacio")
            .put(Genre.BIOGRAPHY, "eletrajz")
            .put(Genre.COMEDY, "vigjatek")
            .put(Genre.CRIME, "bunugyi")
            .put(Genre.DOCUMENTARY, "dokumentumfilm")
            .put(Genre.DRAMA, "drama")
            .put(Genre.EDUCATIONAL, "ismeretterjeszto")
            .put(Genre.FAMILY, "csaladi")
            .put(Genre.FANTASY, "fantasy")
            .put(Genre.HISTORY, "tortenelmi")
            .put(Genre.HORROR, "horror")
            .put(Genre.MUSIC, "zene")
            .put(Genre.MYSTERY, "misztikus")
            .put(Genre.REALITY, "valosagshow")
            .put(Genre.ROMANCE, "romantikus")
            .put(Genre.SCI_FI, "sci-fi")
            .put(Genre.SHORT, "rövidfilm")
            .put(Genre.SPORT, "sport")
            .put(Genre.TALK_SHOW, "talk-show")
            .put(Genre.THRILLER, "thriller")
            .put(Genre.WAR, "haborus")
            .put(Genre.WESTERN, "western")
            .build();

    public static String of(Genre genre) {
        String text = GENRES.getOrDefault(genre, null);
        if (text == null) {
            throw new InvalidConfigurationException(String.format("'%s' genre can not be mapped to ncore-tag!", genre));
        }
        return text;
    }

    private static final ImmutableMap<SortBy, String> SORT_BYS = ImmutableMap.<SortBy, String>builder()
            .put(SortBy.TITLE, "name")
            .put(SortBy.DATE, "fid")
            .put(SortBy.VIEWS, "times_completed")
            .put(SortBy.POPULARITY, "seeders")
            .build();

    public static String of(SortBy sortBy) {
        String text = SORT_BYS.getOrDefault(sortBy, null);
        if (text == null) {
            throw new InvalidConfigurationException(String.format("'%s' sort-by can not be mapped to ncore-sort attribute!", sortBy));
        }
        return text;
    }

    private static final ImmutableMap<SortOrder, String> SORT_ORDERS = ImmutableMap.<SortOrder, String>builder()
            .put(SortOrder.ASCENDING, "ASC")
            .put(SortOrder.DESCENDING, "DESC")
            .build();

    public static String of(SortOrder sortOrder) {
        String text = SORT_ORDERS.getOrDefault(sortOrder, null);
        if (text == null) {
            throw new InvalidConfigurationException(String.format("'%s' sort-order can not be mapped to ncore-sort order!", sortOrder));
        }
        return text;
    }

    private static final ImmutableMap<MediaType, Set<String>> TYPES = ImmutableMap.<MediaType, Set<String>>builder()
            .put(MediaType.MOVIE, new HashSet<>(Arrays.asList("xvid", "dvd", "dvd9", "hd")))
            .put(MediaType.SHOW, new HashSet<>(Arrays.asList("xvidser", "dvdser", "dvd9ser", "hdser")))
            .build();

    public static Set<String> of(MediaType type) {
        Set<String> texts = ApiMappings.of(type, true);
        texts.addAll(ApiMappings.of(type, false));
        return texts;
    }

    public static Set<String> of(MediaType type, boolean isDubbed) {
        Set<String> texts = TYPES.getOrDefault(type, null);
        if (texts == null) {
            throw new InvalidConfigurationException(String.format("'%s' media-type can not be mapped to ncore-type attribute!", type));
        } else if (isDubbed) {
            return texts.stream().map(s -> s.concat("_hun")).collect(Collectors.toSet());
        }
        return texts;
    }

    public static Optional<MediaType> toMediaType(String mediaTypeStr) {
        String normalizedMediaTypeStr = mediaTypeStr.replace("_hun", "");
        return TYPES.entrySet().stream()
                .filter(e -> e.getValue().contains(normalizedMediaTypeStr))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
