package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.utils.Sets;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbGenre;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApiMappings {
    private static final ImmutableMap<Genre, Set<TmdbGenre>> GENRES = ImmutableMap.<Genre, Set<TmdbGenre>>builder()
            .put(Genre.ACTION, asSet(
                    TmdbGenre.of(28, "Action"),
                    TmdbGenre.of(10759, "Action & Adventure")))
            .put(Genre.ADVENTURE, asSet(
                    TmdbGenre.of(12, "Adventure"),
                    TmdbGenre.of(10759, "Action & Adventure")))
            .put(Genre.ANIMATION, asSet(
                    TmdbGenre.of(16, "Animation")))
            .put(Genre.BIOGRAPHY, asSet(
                    TmdbGenre.of(36, "History")))
            .put(Genre.COMEDY, asSet(
                    TmdbGenre.of(35, "Comedy")))
            .put(Genre.CRIME, asSet(
                    TmdbGenre.of(80, "Crime")))
            .put(Genre.DOCUMENTARY, asSet(
                    TmdbGenre.of(99, "Documentary"),
                    TmdbGenre.of(10768, "War & Politics"),
                    TmdbGenre.of(10763, "News")))
            .put(Genre.DRAMA, asSet(
                    TmdbGenre.of(18, "Drama"),
                    TmdbGenre.of(10766, "Soap")))
            .put(Genre.EDUCATIONAL, asSet(
                    TmdbGenre.of(99, "Documentary"),
                    TmdbGenre.of(10763, "News")))
            .put(Genre.FAMILY, asSet(
                    TmdbGenre.of(10751, "Family"),
                    TmdbGenre.of(10762, "Kids"),
                    TmdbGenre.of(10770, "TV Movie")))
            .put(Genre.FANTASY, asSet(
                    TmdbGenre.of(14, "Fantasy"),
                    TmdbGenre.of(10765, "Sci-Fi & Fantasy")))
            .put(Genre.HISTORY, asSet(
                    TmdbGenre.of(36, "History")))
            .put(Genre.HORROR, asSet(
                    TmdbGenre.of(27, "Horror")))
            .put(Genre.MUSIC, asSet(
                    TmdbGenre.of(10402, "Music")))
            .put(Genre.MYSTERY, asSet(
                    TmdbGenre.of(9648, "Mystery")))
            .put(Genre.REALITY, asSet(
                    TmdbGenre.of(10764, "Reality")))
            .put(Genre.ROMANCE, asSet(
                    TmdbGenre.of(10749, "Romance")))
            .put(Genre.SCI_FI, asSet(
                    TmdbGenre.of(878, "Science Fiction"),
                    TmdbGenre.of(10765, "Sci-Fi & Fantasy")))
            .put(Genre.SHORT, Collections.emptySet())
            .put(Genre.SPORT, Collections.emptySet())
            .put(Genre.TALK_SHOW, asSet(
                    TmdbGenre.of(10767, "Talk")))
            .put(Genre.THRILLER, asSet(
                    TmdbGenre.of(53, "Thriller")))
            .put(Genre.WAR, asSet(
                    TmdbGenre.of(10752, "War"),
                    TmdbGenre.of(10768, "War & Politics")))
            .put(Genre.WESTERN, asSet(
                    TmdbGenre.of(37, "Western")))
            .build();

    public static Set<TmdbGenre> of(Genre genre) {
        Set<TmdbGenre> tmdbGenres = GENRES.getOrDefault(genre, null);
        if (tmdbGenres == null) {
            throw new InvalidConfigurationException(String.format("'%s' genre can not be mapped to tmdb-genre!", genre));
        }
        return tmdbGenres;
    }

    public static LinkedHashSet<Genre> toGenresByIds(Set<Integer> tmdbIds) {
        if (tmdbIds == null || tmdbIds.isEmpty()) {
            return Sets.emptyLinkedSet();
        }
        return GENRES.entrySet().stream()
                .filter(e -> e.getValue().stream().map(TmdbGenre::getId).anyMatch(tmdbIds::contains))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static LinkedHashSet<Genre> toGenres(Set<TmdbGenre> tmdbGenres) {
        if (tmdbGenres == null || tmdbGenres.isEmpty()) {
            return Sets.emptyLinkedSet();
        }
        return GENRES.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(tmdbGenres::contains))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<TmdbGenre> asSet(TmdbGenre... items) {
        return new HashSet<>(Arrays.asList(items));
    }
}
