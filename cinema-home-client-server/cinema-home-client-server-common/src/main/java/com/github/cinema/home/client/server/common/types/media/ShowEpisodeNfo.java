package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.utils.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowEpisodeNfo {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date airDate;
    private final Double rating;
    private final Integer seasonNumber;
    private final Integer episodeNumber;
    private final LinkedHashSet<LocaleString> names;
    private final LinkedHashSet<LocaleString> plots;
    private final LinkedHashSet<Image> stills;
    private LinkedHashSet<TorrentNfo> torrents;

    public ShowEpisodeNfo(Integer seasonNumber, Integer episodeNumber, LinkedHashSet<LocaleString> names,
                          LinkedHashSet<LocaleString> plots, Date airDate, LinkedHashSet<Image> stills, Double rating,
                          LinkedHashSet<TorrentNfo> torrents) {
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.names = names;
        this.plots = plots;
        this.airDate = airDate;
        this.stills = stills;
        this.rating = rating;
        this.torrents = torrents;
    }

    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return this.episodeNumber;
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

    public LinkedHashSet<Image> getStills() {
        return this.stills;
    }

    public Double getRating() {
        return this.rating;
    }

    public LinkedHashSet<TorrentNfo> getTorrents() {
        return this.torrents;
    }

    public boolean hasTorrent() {
        return !CollectionUtils.isEmpty(this.torrents);
    }

    public void sortTorrentsBy(Comparator<TorrentNfo> comparator) {
        if (!CollectionUtils.isEmpty(this.torrents)) {
            this.torrents = this.torrents.stream().sorted(comparator)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Override
    public String toString() {
        return "ShowEpisodeNfo{" +
                "seasonNumber=" + this.seasonNumber +
                ", episodeNumber=" + this.episodeNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ShowEpisodeNfo that = (ShowEpisodeNfo) o;
        return this.seasonNumber.equals(that.seasonNumber) &&
                this.episodeNumber.equals(that.episodeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seasonNumber, this.episodeNumber);
    }

    public static ShowEpisodeNfo.Builder builder() {
        return new ShowEpisodeNfo.Builder();
    }

    public static class Builder {
        protected static Logger logger = LoggerFactory.getLogger(ShowEpisodeNfo.Builder.class);
        private Integer seasonNumber;
        private Integer episodeNumber;
        private LinkedHashSet<LocaleString> names;
        private LinkedHashSet<LocaleString> plots;
        private Date airDate;
        private LinkedHashSet<Image> stills;
        private Double rating;
        private LinkedHashSet<TorrentNfo> torrents;

        private Builder() {
        }

        public ShowEpisodeNfo.Builder seasonNumber(Integer seasonNumber) {
            if (seasonNumber == null) {
                return this;
            } else if (this.seasonNumber != null && !this.seasonNumber.equals(seasonNumber)) {
                logger.warn(String.format("Multiple but different season-numbers are given for episode '%s'! ('%s', '%s')", this, this.seasonNumber, seasonNumber));
            } else {
                this.seasonNumber = seasonNumber;
            }
            return this;
        }

        public ShowEpisodeNfo.Builder episodeNumber(Integer episodeNumber) {
            if (episodeNumber == null) {
                return this;
            } else if (this.episodeNumber != null && !this.episodeNumber.equals(episodeNumber)) {
                logger.warn(String.format("Multiple but different episode-numbers are given for episode: '%s'! ('%s', '%s')", this, this.episodeNumber, episodeNumber));
            } else {
                this.episodeNumber = episodeNumber;
            }
            return this;
        }

        public ShowEpisodeNfo.Builder names(LinkedHashSet<LocaleString> names) {
            if (names == null || names.isEmpty()) {
                return this;
            } else if (this.names == null) {
                this.names = new LinkedHashSet<>(names);
            } else {
                this.names.addAll(names);
            }
            return this;
        }

        public ShowEpisodeNfo.Builder name(LocaleString name) {
            if (StringUtils.isEmpty(name)) {
                return this;
            }
            return this.names(Sets.singletonLinkedSet(name));
        }

        public ShowEpisodeNfo.Builder plots(LinkedHashSet<LocaleString> plots) {
            if (plots == null || plots.isEmpty()) {
                return this;
            } else if (this.plots == null) {
                this.plots = new LinkedHashSet<>(plots);
            } else {
                this.plots.addAll(plots);
            }
            return this;
        }

        public ShowEpisodeNfo.Builder plot(LocaleString plot) {
            if (StringUtils.isEmpty(plot)) {
                return this;
            }
            return this.plots(Sets.singletonLinkedSet(plot));
        }

        public ShowEpisodeNfo.Builder airDate(Date airDate) {
            if (airDate == null) {
                return this;
            } else if (this.airDate != null && !this.airDate.equals(airDate)) {
                logger.warn(String.format("Multiple but different episode-air-dates are given for episode: '%s'! ('%s', '%s')", this, this.airDate, airDate));
            } else {
                this.airDate = airDate;
            }
            return this;
        }

        public ShowEpisodeNfo.Builder stills(LinkedHashSet<Image> stills) {
            if (stills == null || stills.isEmpty()) {
                return this;
            } else if (this.stills == null) {
                this.stills = new LinkedHashSet<>(stills);
            } else {
                this.stills.addAll(stills);
            }
            return this;
        }

        public ShowEpisodeNfo.Builder still(Image still) {
            if (still == null) {
                return this;
            }
            return this.stills(Sets.singletonLinkedSet(still));
        }

        public ShowEpisodeNfo.Builder rating(Double rating) {
            if (rating == null) {
                return this;
            } else if (this.rating != null) {
                this.rating = (this.rating + rating) / 2.0;
            } else {
                this.rating = rating;
            }
            return this;
        }

        public ShowEpisodeNfo.Builder torrents(LinkedHashSet<TorrentNfo> torrents) {
            if (torrents == null || torrents.isEmpty()) {
                return this;
            } else if (this.torrents == null) {
                this.torrents = new LinkedHashSet<>(torrents);
            } else {
                this.torrents.addAll(torrents);
            }
            return this;
        }

        public ShowEpisodeNfo.Builder torrent(TorrentNfo torrent) {
            if (torrent == null) {
                return this;
            }
            return this.torrents(Sets.singletonLinkedSet(torrent));
        }

        public ShowEpisodeNfo build() {
            return new ShowEpisodeNfo(this.seasonNumber, this.episodeNumber, this.names, this.plots,
                    this.airDate, this.stills, this.rating, this.torrents);
        }

        public ShowEpisodeNfo build(ShowEpisodeNfo... nfos) {
            for (ShowEpisodeNfo nfo : nfos) {
                if (nfo == null) {
                    continue;
                }
                this.seasonNumber(nfo.getSeasonNumber())
                        .episodeNumber(nfo.getEpisodeNumber())
                        .names(nfo.getNames())
                        .plots(nfo.getPlots())
                        .airDate(nfo.getAirDate())
                        .stills(nfo.getStills())
                        .rating(nfo.getRating())
                        .torrents(nfo.getTorrents());
            }
            return this.build();
        }

        @Override
        public String toString() {
            return "ShowEpisodeNfo.Builder{" +
                    ", seasonNumber=" + this.seasonNumber +
                    ", episodeNumber=" + this.episodeNumber +
                    '}';
        }
    }
}
