package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.utils.Sets;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowNfo extends MediaNfo {
    private final ShowStatus status;
    private final Integer seasonCount;
    private final Integer episodeCount;
    private final ShowEpisodeNfo lastEpisode;
    private final ShowEpisodeNfo nextEpisode;

    private final List<ShowSeasonNfo> seasons;
    private LinkedHashSet<TorrentNfo> completeTorrents;
    private LinkedHashSet<TorrentNfo> unassignedTorrents;

    private ShowNfo(ImdbId imdbId, TmdbId tmdbId, TvdbId tvdbId, LinkedHashSet<LocaleString> titles,
                    LinkedHashSet<Genre> genres, LinkedHashSet<LocaleString> plots, Double runtime, Double rating,
                    Integer releaseYear, MediaCollection collection, LinkedHashSet<NetworkGroup> networks,
                    LinkedHashSet<String> creators, LinkedHashSet<Actor> actors, LinkedHashSet<Image> posterUrls,
                    LinkedHashSet<Image> backgroundUrls, LinkedHashSet<Image> logoUrls, LinkedHashSet<Image> thumbUrls,
                    LinkedHashSet<Video> videos, ShowStatus status, Integer seasonCount, Integer episodeCount,
                    ShowEpisodeNfo lastEpisode, ShowEpisodeNfo nextEpisode, List<ShowSeasonNfo> seasons,
                    LinkedHashSet<TorrentNfo> completeTorrents, LinkedHashSet<TorrentNfo> unassignedTorrents) {
        super(imdbId, tmdbId, tvdbId, titles, genres, plots, runtime, rating, releaseYear, collection, networks, creators, actors, posterUrls, backgroundUrls, logoUrls, thumbUrls, videos);
        this.status = status;
        this.seasonCount = seasonCount;
        this.episodeCount = episodeCount;
        this.lastEpisode = lastEpisode;
        this.nextEpisode = nextEpisode;
        this.seasons = seasons;
        this.completeTorrents = completeTorrents;
        this.unassignedTorrents = unassignedTorrents;
    }

    public ShowStatus getStatus() {
        return this.status;
    }

    public Integer getSeasonCount() {
        return this.seasonCount;
    }

    public Integer getEpisodeCount() {
        return this.episodeCount;
    }

    public ShowEpisodeNfo getLastEpisode() {
        return this.lastEpisode;
    }

    public ShowEpisodeNfo getNextEpisode() {
        return this.nextEpisode;
    }

    public List<ShowSeasonNfo> getSeasons() {
        return this.seasons;
    }

    public LinkedHashSet<TorrentNfo> getCompleteTorrents() {
        return this.completeTorrents;
    }

    public LinkedHashSet<TorrentNfo> getUnassignedTorrents() {
        return this.unassignedTorrents;
    }

    @Override
    public boolean hasTorrent() {
        if (!CollectionUtils.isEmpty(this.completeTorrents) || !CollectionUtils.isEmpty(this.unassignedTorrents)) {
            return true;
        }
        if (this.seasons != null) {
            for (ShowSeasonNfo season : this.seasons) {
                if (season.hasTorrent()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sortTorrentsBy(Comparator<TorrentNfo> comparator) {
        if (!CollectionUtils.isEmpty(this.completeTorrents)) {
            this.completeTorrents = this.completeTorrents.stream().sorted(comparator)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        if (!CollectionUtils.isEmpty(this.unassignedTorrents)) {
            this.unassignedTorrents = this.unassignedTorrents.stream().sorted(comparator)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        if (!CollectionUtils.isEmpty(this.seasons)) {
            for (ShowSeasonNfo season : this.seasons) {
                season.sortTorrentsBy(comparator);
            }
        }
    }

    @Override
    public String toString() {
        return "ShowNfo{" +
                "imdbId=" + this.imdbId +
                ", tmdbId=" + this.tmdbId +
                ", tvdbId=" + this.tvdbId +
                ", titles=" + this.titles +
                '}';
    }

    public static ShowNfo.Builder builder() {
        return new ShowNfo.Builder();
    }

    public static class Builder extends MediaNfo.Builder<ShowNfo, ShowNfo.Builder> {
        private ShowStatus status;
        private Integer seasonCount;
        private Integer episodeCount;
        private ShowEpisodeNfo lastEpisode;
        private ShowEpisodeNfo nextEpisode;
        private List<ShowSeasonNfo> seasons;
        private LinkedHashSet<TorrentNfo> completeTorrents;
        private LinkedHashSet<TorrentNfo> unassignedTorrents;

        private Builder() {
        }

        public ShowNfo.Builder status(ShowStatus status) {
            if (status == null) {
                return this.self();
            } else if (this.status != null && !this.status.equals(status)) {
                logger.warn(String.format("Multiple but different statuses are given! ('%s', '%s') for show: '%s'", this.status, status, this.imdbId));
            } else {
                this.status = status;
            }
            return this.self();
        }

        public ShowNfo.Builder seasonCount(Integer seasonCount) {
            if (seasonCount == null) {
                return this;
            } else if (this.seasonCount != null && !this.seasonCount.equals(seasonCount)) {
                logger.warn(String.format("Multiple but different season-count value given! ('%s', '%s') for show: '%s'", this.seasonCount, seasonCount, this.imdbId));
            } else {
                this.seasonCount = seasonCount;
            }
            return this;
        }

        public ShowNfo.Builder episodeCount(Integer episodeCount) {
            if (episodeCount == null) {
                return this;
            } else if (this.episodeCount != null && !this.episodeCount.equals(episodeCount)) {
                logger.warn(String.format("Multiple but different episode-count value given! ('%s', '%s') for show: '%s'", this.episodeCount, episodeCount, this.imdbId));
            } else {
                this.episodeCount = episodeCount;
            }
            return this;
        }

        public ShowNfo.Builder lastEpisode(ShowEpisodeNfo lastEpisode) {
            if (lastEpisode == null) {
                return this;
            } else if (this.lastEpisode != null && !this.lastEpisode.equals(lastEpisode)) {
                logger.warn(String.format("Multiple but different last-episodes are given! ('%s', '%s') for show: '%s'", this.lastEpisode, lastEpisode, this.imdbId));
            } else {
                this.lastEpisode = lastEpisode;
            }
            return this;
        }

        public ShowNfo.Builder nextEpisode(ShowEpisodeNfo nextEpisode) {
            if (nextEpisode == null) {
                return this;
            } else if (this.nextEpisode != null && !this.nextEpisode.equals(nextEpisode)) {
                logger.warn(String.format("Multiple but different next-episodes are given! ('%s', '%s') for show: '%s'", this.nextEpisode, nextEpisode, this.imdbId));
            } else {
                this.nextEpisode = nextEpisode;
            }
            return this;
        }

        public ShowNfo.Builder seasons(List<ShowSeasonNfo> seasons) {
            if (seasons == null || seasons.isEmpty()) {
                return this;
            } else if (this.seasons == null) {
                this.seasons = new ArrayList<>(seasons);
            } else {
                for (ShowSeasonNfo season : seasons) {
                    Optional<ShowSeasonNfo> current = this.seasons.stream()
                            .filter(s -> s.getSeasonNumber().equals(season.getSeasonNumber()))
                            .findFirst();
                    if (current.isPresent()) {
                        this.seasons.add(ShowSeasonNfo.builder().build(current.get(), season));
                        this.seasons.remove(current.get());
                    } else {
                        this.seasons.add(season);
                    }
                }
            }
            return this;
        }

        public ShowNfo.Builder season(ShowSeasonNfo season) {
            if (season == null) {
                return this;
            }
            return this.seasons(Collections.singletonList(season));
        }

        public ShowNfo.Builder completeTorrents(LinkedHashSet<TorrentNfo> completeTorrents) {
            if (completeTorrents == null || completeTorrents.isEmpty()) {
                return this;
            } else if (this.completeTorrents == null) {
                this.completeTorrents = new LinkedHashSet<>(completeTorrents);
            } else {
                this.completeTorrents.addAll(completeTorrents);
            }
            return this;
        }

        public ShowNfo.Builder completeTorrent(TorrentNfo completeTorrent) {
            if (completeTorrent == null) {
                return this;
            }
            return this.completeTorrents(Sets.singletonLinkedSet(completeTorrent));
        }

        public ShowNfo.Builder unassignedTorrents(LinkedHashSet<TorrentNfo> unassignedTorrents) {
            if (unassignedTorrents == null || unassignedTorrents.isEmpty()) {
                return this;
            } else if (this.unassignedTorrents == null) {
                this.unassignedTorrents = new LinkedHashSet<>(unassignedTorrents);
            } else {
                this.unassignedTorrents.addAll(unassignedTorrents);
            }
            return this;
        }

        public ShowNfo.Builder unassignedTorrent(TorrentNfo unassignedTorrent) {
            if (unassignedTorrent == null) {
                return this;
            }
            return this.unassignedTorrents(Sets.singletonLinkedSet(unassignedTorrent));
        }

        @Override
        public ShowNfo build() {
            return new ShowNfo(this.imdbId, this.tmdbId, this.tvdbId, this.titles, this.genres, this.plots, this.runtime,
                    this.rating, this.releaseYear, this.collection, this.networks, this.creators, this.actors,
                    this.posters, this.backdrops, this.logos, this.thumbs, this.videos, this.status,
                    this.seasonCount, this.episodeCount, this.lastEpisode, this.nextEpisode, this.seasons,
                    this.completeTorrents, this.unassignedTorrents);
        }

        public ShowNfo build(ShowNfo... nfos) {
            for (ShowNfo nfo : nfos) {
                if (nfo == null) {
                    continue;
                }
                super.merge(nfo)
                        .status(nfo.getStatus())
                        .seasonCount(nfo.getSeasonCount())
                        .episodeCount(nfo.getEpisodeCount())
                        .lastEpisode(nfo.getLastEpisode())
                        .nextEpisode(nfo.getNextEpisode())
                        .seasons(nfo.getSeasons())
                        .completeTorrents(nfo.getCompleteTorrents())
                        .unassignedTorrents(nfo.getUnassignedTorrents());
            }
            return this.build();
        }
    }
}
