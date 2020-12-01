package com.github.cinema.home.client.server.torrent.title.types;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.types.media.ReleaseNfo;
import com.github.cinema.home.client.server.common.types.tech.AudioCodec;
import com.github.cinema.home.client.server.common.types.tech.ColorCodec;
import com.github.cinema.home.client.server.common.types.tech.Container;
import com.github.cinema.home.client.server.common.types.tech.Edition;
import com.github.cinema.home.client.server.common.types.tech.Language;
import com.github.cinema.home.client.server.common.types.tech.Resolution;
import com.github.cinema.home.client.server.common.types.tech.Scanning;
import com.github.cinema.home.client.server.common.types.tech.Source;
import com.github.cinema.home.client.server.common.types.tech.VideoCodec;
import org.springframework.data.domain.Range;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TitleNfo {
    private final String torrentTitle;
    private final String mediaTitle;
    private final Range<Integer> releaseYears;
    private final Date releaseDate;
    private final EpisodeId episodeId;

    private final NetworkGroup network;
    private final String group;

    private final Set<Source> sources;
    private final Edition edition;
    private final Container container;

    private final Resolution resolution;
    private final Scanning scanning;
    private final VideoCodec videoCodec;
    private final ColorCodec colorCodec;
    private final Boolean hdr;

    private final Set<AudioCodec> audioCodecs;
    private final Set<String> audioChannels;
    private final Set<Language> audioLanguages;
    private final Set<Language> subtitleLanguages;

    private final Boolean hardcoded;
    private final Boolean documentary;
    private final Boolean widescreen;
    private final Boolean blackAndWhite;
    private final Boolean ddd;
    private final Boolean complete;

    private TitleNfo(String torrentTitle, String mediaTitle, Range<Integer> releaseYears, Date releaseDate,
                     EpisodeId episodeId, NetworkGroup network, String group, Set<Source> sources,
                     Edition edition, Container container, Resolution resolution, Scanning scanning,
                     VideoCodec videoCodec, ColorCodec colorCodec, Boolean hdr, Set<AudioCodec> audioCodecs,
                     Set<String> audioChannels, Set<Language> audioLanguages, Set<Language> subtitleLanguages,
                     Boolean hardcoded, Boolean documentary, Boolean widescreen, Boolean blackAndWhite, Boolean ddd,
                     Boolean complete) {
        this.torrentTitle = torrentTitle;
        this.mediaTitle = mediaTitle;
        this.releaseYears = releaseYears;
        this.releaseDate = releaseDate;
        this.episodeId = episodeId;
        this.network = network;
        this.group = group;
        this.sources = sources;
        this.edition = edition;
        this.container = container;
        this.resolution = resolution;
        this.scanning = scanning;
        this.videoCodec = videoCodec;
        this.colorCodec = colorCodec;
        this.hdr = hdr;
        this.audioCodecs = audioCodecs;
        this.audioChannels = audioChannels;
        this.audioLanguages = audioLanguages;
        this.subtitleLanguages = subtitleLanguages;
        this.hardcoded = hardcoded;
        this.documentary = documentary;
        this.widescreen = widescreen;
        this.blackAndWhite = blackAndWhite;
        this.ddd = ddd;
        this.complete = complete;
    }

    public ReleaseNfo toReleaseNfo() {
        return ReleaseNfo.builder()
                .group(this.group)
                .sources(this.sources)
                .edition(this.edition)
                .container(this.container)
                .resolution(this.resolution)
                .scanning(this.scanning)
                .videoCodec(this.videoCodec)
                .colorCodec(this.colorCodec)
                .hdr(this.hdr)
                .audioCodecs(this.audioCodecs)
                .audioChannels(this.audioChannels)
                .audioLanguages(this.audioLanguages)
                .subtitleLanguages(this.subtitleLanguages)
                .hardcoded(this.hardcoded)
                .widescreen(this.widescreen)
                .blackAndWhite(this.blackAndWhite)
                .ddd(this.ddd)
                .complete(this.complete)
                .build();
    }

    public String getTorrentTitle() {
        return this.torrentTitle;
    }

    public String getMediaTitle() {
        return this.mediaTitle;
    }

    public Range<Integer> getReleaseYears() {
        return this.releaseYears;
    }

    public Optional<Integer> getReleaseYearIfNotRange() {
        if (this.releaseYears == null) {
            return Optional.empty();
        }
        Optional<Integer> lower = this.releaseYears.getLowerBound().getValue();
        Optional<Integer> upper = this.releaseYears.getUpperBound().getValue();
        boolean isJust = lower.isPresent() && (!upper.isPresent() || lower.get().equals(upper.get()));
        return isJust ? lower : Optional.empty();
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public EpisodeId getEpisodeId() {
        return this.episodeId;
    }

    public NetworkGroup getNetwork() {
        return this.network;
    }

    public String getGroup() {
        return this.group;
    }

    public Set<Source> getSources() {
        return this.sources;
    }

    public Edition getEdition() {
        return this.edition;
    }

    public Container getContainer() {
        return this.container;
    }

    public Resolution getResolution() {
        return this.resolution;
    }

    public Scanning getScanning() {
        return this.scanning;
    }

    public VideoCodec getVideoCodec() {
        return this.videoCodec;
    }

    public ColorCodec getColorCodec() {
        return this.colorCodec;
    }

    public Boolean isHdr() {
        return this.hdr;
    }

    public Set<AudioCodec> getAudioCodecs() {
        return this.audioCodecs;
    }

    public Set<String> getAudioChannels() {
        return this.audioChannels;
    }

    public Set<Language> getAudioLanguages() {
        return this.audioLanguages;
    }

    public Set<Language> getSubtitleLanguages() {
        return this.subtitleLanguages;
    }

    public Boolean isHardcoded() {
        return this.hardcoded;
    }

    public Boolean isDocumentary() {
        return this.documentary;
    }

    public Boolean isWidescreen() {
        return this.widescreen;
    }

    public Boolean isBlackAndWhite() {
        return this.blackAndWhite;
    }

    public Boolean is3d() {
        return this.ddd;
    }

    public Boolean isComplete() {
        return this.complete;
    }

    public static TitleNfo.Builder builder() {
        return new TitleNfo.Builder();
    }

    public static class Builder {
        private String torrentTitle;
        private String mediaTitle;
        private Range<Integer> releaseYears;
        private Date releaseDate;
        private EpisodeId episodeId;
        private NetworkGroup network;
        private String group;
        private Set<Source> sources;
        private Edition edition;
        private Container container;
        private Integer diskCount;
        private Resolution resolution;
        private Scanning scanning;
        private VideoCodec videoCodec;
        private ColorCodec colorCodec;
        private Boolean hdr;
        private Set<AudioCodec> audioCodecs;
        private Set<String> audioChannels;
        private Set<Language> audioLanguages;
        private Set<Language> subtitleLanguages;
        private Boolean hardcoded;
        private Boolean documentary;
        private Boolean widescreen;
        private Boolean blackAndWhite;
        private Boolean ddd;
        private Boolean complete;

        private Builder() {
        }

        public TitleNfo.Builder torrentTitle(String torrentTitle) {
            this.torrentTitle = torrentTitle;
            return this;
        }

        public TitleNfo.Builder mediaTitle(String mediaTitle) {
            this.mediaTitle = mediaTitle;
            return this;
        }

        public TitleNfo.Builder releaseYears(Range<Integer> releaseYears) {
            this.releaseYears = releaseYears;
            return this;
        }

        public TitleNfo.Builder releaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public TitleNfo.Builder episodeId(EpisodeId episodeId) {
            this.episodeId = episodeId;
            return this;
        }

        public TitleNfo.Builder network(NetworkGroup network) {
            this.network = network;
            return this;
        }

        public TitleNfo.Builder group(String group) {
            this.group = group;
            return this;
        }

        public TitleNfo.Builder sources(Set<Source> sources) {
            if (sources == null || sources.isEmpty()) {
                return this;
            } else if (this.sources == null) {
                this.sources = new HashSet<>(sources);
            } else {
                this.sources.addAll(sources);
            }
            return this;
        }

        public TitleNfo.Builder source(Source source) {
            if (StringUtils.isEmpty(source)) {
                return this;
            }
            return this.sources(Collections.singleton(source));
        }

        public TitleNfo.Builder edition(Edition edition) {
            this.edition = edition;
            return this;
        }

        public TitleNfo.Builder container(Container container) {
            this.container = container;
            return this;
        }

        public TitleNfo.Builder resolution(Resolution resolution) {
            this.resolution = resolution;
            return this;
        }

        public TitleNfo.Builder scanning(Scanning scanning) {
            this.scanning = scanning;
            return this;
        }

        public TitleNfo.Builder videoCodec(VideoCodec videoCodec) {
            this.videoCodec = videoCodec;
            return this;
        }

        public TitleNfo.Builder colorCodec(ColorCodec colorCodec) {
            this.colorCodec = colorCodec;
            return this;
        }

        public TitleNfo.Builder hdr(Boolean hdr) {
            this.hdr = hdr;
            return this;
        }

        public TitleNfo.Builder audioCodecs(Set<AudioCodec> audioCodecs) {
            if (audioCodecs == null || audioCodecs.isEmpty()) {
                return this;
            } else if (this.audioCodecs == null) {
                this.audioCodecs = new HashSet<>(audioCodecs);
            } else {
                this.audioCodecs.addAll(audioCodecs);
            }
            return this;
        }

        public TitleNfo.Builder audioCodec(AudioCodec audioCodec) {
            if (audioCodec == null) {
                return this;
            }
            return this.audioCodecs(Collections.singleton(audioCodec));
        }

        public TitleNfo.Builder audioChannels(Set<String> audioChannels) {
            if (audioChannels == null || audioChannels.isEmpty()) {
                return this;
            } else if (this.audioChannels == null) {
                this.audioChannels = new HashSet<>(audioChannels);
            } else {
                this.audioChannels.addAll(audioChannels);
            }
            return this;
        }

        public TitleNfo.Builder audioChannel(String audioChannel) {
            if (StringUtils.isEmpty(audioChannel)) {
                return this;
            }
            return this.audioChannels(Collections.singleton(audioChannel));
        }

        public TitleNfo.Builder audioLanguages(Set<Language> audioLanguages) {
            if (audioLanguages == null || audioLanguages.isEmpty()) {
                return this;
            } else if (this.audioLanguages == null) {
                this.audioLanguages = new HashSet<>(audioLanguages);
            } else {
                this.audioLanguages.addAll(audioLanguages);
            }
            return this;
        }

        public TitleNfo.Builder audioLanguage(Language audioLanguage) {
            if (audioLanguage == null) {
                return this;
            }
            return this.audioLanguages(Collections.singleton(audioLanguage));
        }

        public TitleNfo.Builder subtitleLanguages(Set<Language> subtitleLanguages) {
            if (subtitleLanguages == null || subtitleLanguages.isEmpty()) {
                return this;
            } else if (this.subtitleLanguages == null) {
                this.subtitleLanguages = new HashSet<>(subtitleLanguages);
            } else {
                this.subtitleLanguages.addAll(subtitleLanguages);
            }
            return this;
        }

        public TitleNfo.Builder subtitleLanguage(Language subtitleLanguage) {
            if (subtitleLanguage == null) {
                return this;
            }
            return this.subtitleLanguages(Collections.singleton(subtitleLanguage));
        }

        public TitleNfo.Builder hardcoded(Boolean hardcoded) {
            this.hardcoded = hardcoded;
            return this;
        }

        public TitleNfo.Builder documentary(Boolean documentary) {
            this.documentary = documentary;
            return this;
        }

        public TitleNfo.Builder widescreen(Boolean widescreen) {
            this.widescreen = widescreen;
            return this;
        }

        public TitleNfo.Builder blackAndWhite(Boolean blackAndWhite) {
            this.blackAndWhite = blackAndWhite;
            return this;
        }

        public TitleNfo.Builder ddd(Boolean ddd) {
            this.ddd = ddd;
            return this;
        }

        public TitleNfo.Builder complete(Boolean complete) {
            this.complete = complete;
            return this;
        }

        public TitleNfo build() {
            return new TitleNfo(this.torrentTitle, this.mediaTitle, this.releaseYears, this.releaseDate, this.episodeId,
                    this.network, this.group, this.sources, this.edition, this.container, this.resolution,
                    this.scanning, this.videoCodec, this.colorCodec, this.hdr, this.audioCodecs, this.audioChannels,
                    this.audioLanguages, this.subtitleLanguages, this.hardcoded, this.documentary, this.widescreen,
                    this.blackAndWhite, this.ddd, this.complete);
        }
    }
}
