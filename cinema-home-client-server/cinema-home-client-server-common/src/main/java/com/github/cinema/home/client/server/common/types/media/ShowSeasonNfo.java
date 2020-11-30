package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.utils.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowSeasonNfo {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date airDate;
    private final Integer seasonNumber;
    private final Integer episodeCount;
    private final LinkedHashSet<LocaleString> names;
    private final LinkedHashSet<LocaleString> plots;
    private final LinkedHashSet<Image> posters;
    private final List<ShowEpisodeNfo> episodes;
    private LinkedHashSet<TorrentNfo> torrents;

    public ShowSeasonNfo(Integer seasonNumber, LinkedHashSet<LocaleString> names,
                         LinkedHashSet<LocaleString> plots, Date airDate, LinkedHashSet<Image> posters,
                         Integer episodesCount, List<ShowEpisodeNfo> episodes, LinkedHashSet<TorrentNfo> torrents) {
        this.seasonNumber = seasonNumber;
        this.names = names;
        this.plots = plots;
        this.airDate = airDate;
        this.posters = posters;
        this.episodeCount = episodesCount;
        this.episodes = episodes;
        this.torrents = torrents;
    }

    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public LinkedHashSet<LocaleString> getNames() {
        return this.names;
    }

    public LinkedHashSet<LocaleString> getPlots() {
        return this.plots;
    }

    public Date getAirDate() {
        return this.airDate;
    }

    public LinkedHashSet<Image> getPosters() {
        return this.posters;
    }

    public Integer getEpisodeCount() {
        return this.episodeCount;
    }

    public List<ShowEpisodeNfo> getEpisodes() {
        return this.episodes;
    }

    public LinkedHashSet<TorrentNfo> getTorrents() {
        return this.torrents;
    }

    public boolean hasTorrent() {
        if (!CollectionUtils.isEmpty(this.torrents)) {
            return true;
        }
        if (this.episodes != null) {
            for (ShowEpisodeNfo episode : this.episodes) {
                if (episode.hasTorrent()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void sortTorrentsBy(Comparator<TorrentNfo> comparator) {
        if (!CollectionUtils.isEmpty(this.torrents)) {
            this.torrents = this.torrents.stream().sorted(comparator)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        if (!CollectionUtils.isEmpty(this.episodes)) {
            for (ShowEpisodeNfo episode : this.episodes) {
                episode.sortTorrentsBy(comparator);
            }
        }
    }

    @Override
    public String toString() {
        return "ShowSeasonNfo{" +
                "seasonNumber=" + this.seasonNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ShowSeasonNfo that = (ShowSeasonNfo) o;
        return this.seasonNumber.equals(that.seasonNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seasonNumber);
    }

    public static ShowSeasonNfo.Builder builder() {
        return new ShowSeasonNfo.Builder();
    }

    public static class Builder {
        private static final Logger logger = LoggerFactory.getLogger(ShowSeasonNfo.Builder.class);
        private ImdbId showId;
        private Integer seasonNumber;
        private LinkedHashSet<LocaleString> names;
        private LinkedHashSet<LocaleString> plots;
        private Date airDate;
        private LinkedHashSet<Image> posters;
        private Integer episodeCount;
        private List<ShowEpisodeNfo> episodes;
        private LinkedHashSet<TorrentNfo> torrents;

        private Builder() {
        }

        public ShowSeasonNfo.Builder seasonNumber(Integer seasonNumber) {
            if (seasonNumber == null) {
                return this;
            } else if (this.seasonNumber != null && !this.seasonNumber.equals(seasonNumber)) {
                logger.warn(String.format("Multiple but different season-numbers are given! ('%s', '%s')", this.seasonNumber, seasonNumber));
            } else {
                this.seasonNumber = seasonNumber;
            }
            return this;
        }

        public ShowSeasonNfo.Builder names(LinkedHashSet<LocaleString> names) {
            if (names == null || names.isEmpty()) {
                return this;
            } else if (this.names == null) {
                this.names = new LinkedHashSet<>(names);
            } else {
                this.names.addAll(names);
            }
            return this;
        }

        public ShowSeasonNfo.Builder name(LocaleString name) {
            if (StringUtils.isEmpty(name)) {
                return this;
            }
            return this.names(Sets.singletonLinkedSet(name));
        }

        public ShowSeasonNfo.Builder plots(LinkedHashSet<LocaleString> plots) {
            if (plots == null || plots.isEmpty()) {
                return this;
            } else if (this.plots == null) {
                this.plots = new LinkedHashSet<>(plots);
            } else {
                this.plots.addAll(plots);
            }
            return this;
        }

        public ShowSeasonNfo.Builder plot(LocaleString plot) {
            if (StringUtils.isEmpty(plot)) {
                return this;
            }
            return this.plots(Sets.singletonLinkedSet(plot));
        }

        public ShowSeasonNfo.Builder airDate(Date airDate) {
            if (airDate == null) {
                return this;
            } else if (this.airDate != null && !this.airDate.equals(airDate)) {
                logger.warn(String.format("Multiple but different episode-air-dates are given! ('%s', '%s')", this.airDate, airDate));
            } else {
                this.airDate = airDate;
            }
            return this;
        }

        public ShowSeasonNfo.Builder posters(LinkedHashSet<Image> posters) {
            if (posters == null || posters.isEmpty()) {
                return this;
            } else if (this.posters == null) {
                this.posters = new LinkedHashSet<>(posters);
            } else {
                this.posters.addAll(posters);
            }
            return this;
        }

        public ShowSeasonNfo.Builder episodeCount(Integer episodeCount) {
            if (episodeCount == null) {
                return this;
            } else if (this.episodeCount != null && !this.episodeCount.equals(episodeCount)) {
                logger.warn(String.format("Multiple but different episode-count are given! ('%s', '%s')", this.episodeCount, episodeCount));
            } else {
                this.episodeCount = episodeCount;
            }
            return this;
        }

        public ShowSeasonNfo.Builder episodes(List<ShowEpisodeNfo> episodes) {
            if (episodes == null || episodes.isEmpty()) {
                return this;
            } else if (this.episodes == null) {
                this.episodes = new ArrayList<>(episodes);
            } else {
                for (ShowEpisodeNfo episode : episodes) {
                    Optional<ShowEpisodeNfo> current = this.episodes.stream()
                            .filter(e -> e.getEpisodeNumber().equals(episode.getEpisodeNumber()))
                            .findFirst();
                    if (current.isPresent()) {
                        this.episodes.add(ShowEpisodeNfo.builder().build(current.get(), episode));
                        this.episodes.remove(current.get());
                    } else {
                        this.episodes.add(episode);
                    }
                }
            }
            return this;
        }

        public ShowSeasonNfo.Builder episode(ShowEpisodeNfo episode) {
            if (episode == null) {
                return this;
            }
            return this.episodes(Collections.singletonList(episode));
        }

        public ShowSeasonNfo.Builder torrents(LinkedHashSet<TorrentNfo> torrents) {
            if (torrents == null || torrents.isEmpty()) {
                return this;
            } else if (this.torrents == null) {
                this.torrents = new LinkedHashSet<>(torrents);
            } else {
                this.torrents.addAll(torrents);
            }
            return this;
        }

        public ShowSeasonNfo.Builder torrent(TorrentNfo torrent) {
            if (torrent == null) {
                return this;
            }
            return this.torrents(Sets.singletonLinkedSet(torrent));
        }

        public ShowSeasonNfo build() {
            return new ShowSeasonNfo(this.seasonNumber, this.names, this.plots, this.airDate, this.posters,
                    this.episodeCount, this.episodes, this.torrents);
        }

        public ShowSeasonNfo build(ShowSeasonNfo... nfos) {
            for (ShowSeasonNfo nfo : nfos) {
                if (nfo == null) {
                    continue;
                }
                this.seasonNumber(nfo.getSeasonNumber())
                        .names(nfo.getNames())
                        .plots(nfo.getPlots())
                        .airDate(nfo.getAirDate())
                        .posters(nfo.getPosters())
                        .episodeCount(nfo.getEpisodeCount())
                        .episodes(nfo.getEpisodes())
                        .torrents(nfo.getTorrents());
            }
            return this.build();
        }
    }
}
