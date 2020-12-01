package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.utils.Sets;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieNfo extends MediaNfo {
    private final MovieStatus status;
    private final LinkedHashSet<LocaleString> taglines;

    private LinkedHashSet<TorrentNfo> torrents;

    private MovieNfo(ImdbId imdbId, TmdbId tmdbId, TvdbId tvdbId, LinkedHashSet<LocaleString> titles,
                     LinkedHashSet<Genre> genres, LinkedHashSet<LocaleString> plots, Double runtime, Double rating,
                     Integer releaseYear, MediaCollection collection, LinkedHashSet<NetworkGroup> networks,
                     LinkedHashSet<String> creators, LinkedHashSet<Actor> actors, LinkedHashSet<Image> posterUrls,
                     LinkedHashSet<Image> backgroundUrls, LinkedHashSet<Image> logoUrls, LinkedHashSet<Image> thumbUrls,
                     LinkedHashSet<Video> videos, MovieStatus status, LinkedHashSet<LocaleString> taglines,
                     LinkedHashSet<TorrentNfo> torrents) {
        super(imdbId, tmdbId, tvdbId, titles, genres, plots, runtime, rating, releaseYear, collection, networks, creators, actors, posterUrls, backgroundUrls, logoUrls, thumbUrls, videos);
        this.status = status;
        this.taglines = taglines;
        this.torrents = torrents;
    }

    public MovieStatus getStatus() {
        return this.status;
    }

    public LinkedHashSet<LocaleString> getTaglines() {
        return this.taglines;
    }

    public LinkedHashSet<TorrentNfo> getTorrents() {
        return this.torrents;
    }

    @Override
    public boolean hasTorrent() {
        return !CollectionUtils.isEmpty(this.torrents);
    }

    @Override
    public void sortTorrentsBy(Comparator<TorrentNfo> comparator) {
        if (!CollectionUtils.isEmpty(this.torrents)) {
            this.torrents = this.torrents.stream().sorted(comparator).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Override
    public String toString() {
        return "MovieNfo{" +
                "imdbId=" + this.imdbId +
                ", tmdbId=" + this.tmdbId +
                ", tvdbId=" + this.tvdbId +
                ", titles=" + this.titles +
                '}';
    }

    public static MovieNfo.Builder builder() {
        return new MovieNfo.Builder();
    }

    public static class Builder extends MediaNfo.Builder<MovieNfo, MovieNfo.Builder> {
        private MovieStatus status;
        private LinkedHashSet<LocaleString> taglines;
        private LinkedHashSet<TorrentNfo> torrents;

        private Builder() {
        }

        public MovieNfo.Builder status(MovieStatus status) {
            if (status == null) {
                return this.self();
            } else if (this.status != null && !this.status.equals(status)) {
                logger.warn(String.format("Multiple but different statuses are given! ('%s', '%s')", this.status, status));
            } else {
                this.status = status;
            }
            return this.self();
        }

        public MovieNfo.Builder taglines(LinkedHashSet<LocaleString> taglines) {
            if (taglines == null || taglines.isEmpty()) {
                return this;
            } else if (this.taglines == null) {
                this.taglines = new LinkedHashSet<>(taglines);
            } else {
                this.taglines.addAll(taglines);
            }
            return this;
        }

        public MovieNfo.Builder tagline(LocaleString tagline) {
            if (StringUtils.isEmpty(tagline)) {
                return this.self();
            }
            return this.taglines(Sets.singletonLinkedSet(tagline));
        }

        public MovieNfo.Builder torrents(LinkedHashSet<TorrentNfo> torrents) {
            if (torrents == null || torrents.isEmpty()) {
                return this;
            } else if (this.torrents == null) {
                this.torrents = new LinkedHashSet<>(torrents);
            } else {
                this.torrents.addAll(torrents);
            }
            return this;
        }

        public MovieNfo.Builder torrent(TorrentNfo torrent) {
            if (torrent == null) {
                return this;
            }
            return this.torrents(Sets.singletonLinkedSet(torrent));
        }

        @Override
        public MovieNfo build() {
            return new MovieNfo(this.imdbId, this.tmdbId, this.tvdbId, this.titles, this.genres, this.plots,
                    this.runtime, this.rating, this.releaseYear, this.collection, this.networks, this.creators,
                    this.actors, this.posters, this.backdrops, this.logos, this.thumbs, this.videos,
                    this.status, this.taglines, this.torrents);
        }

        public MovieNfo build(MovieNfo... nfos) {
            for (MovieNfo nfo : nfos) {
                if (nfo == null) {
                    continue;
                }
                super.merge(nfo)
                        .status(nfo.getStatus())
                        .taglines(nfo.getTaglines())
                        .torrents(nfo.getTorrents());
            }
            return this.build();
        }
    }
}
