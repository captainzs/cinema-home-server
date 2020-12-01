package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.tech.AudioCodec;
import com.github.cinema.home.client.server.common.types.tech.ColorCodec;
import com.github.cinema.home.client.server.common.types.tech.Container;
import com.github.cinema.home.client.server.common.types.tech.Edition;
import com.github.cinema.home.client.server.common.types.tech.Language;
import com.github.cinema.home.client.server.common.types.tech.Resolution;
import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;
import com.github.cinema.home.client.server.common.types.tech.Scanning;
import com.github.cinema.home.client.server.common.types.tech.Source;
import com.github.cinema.home.client.server.common.types.tech.VideoCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReleaseNfo {
    private final String group;

    private final Set<Source> sources;
    private final Edition edition;
    private final Container container;
    private final Long fullSize;

    private final ResolutionDefinition resolutionDefinition;
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
    private final Boolean widescreen;
    private final Boolean blackAndWhite;
    private final Boolean ddd;
    private final Boolean complete;

    private ReleaseNfo(String group, Set<Source> sources, Edition edition,
                       Container container, Long fullSize, ResolutionDefinition resolutionDefinition,
                       Resolution resolution, Scanning scanning, VideoCodec videoCodec, ColorCodec colorCodec,
                       Boolean hdr, Set<AudioCodec> audioCodecs, Set<String> audioChannels, Set<Language> audioLanguages,
                       Set<Language> subtitleLanguages, Boolean hardcoded, Boolean widescreen, Boolean blackAndWhite,
                       Boolean ddd, Boolean complete) {
        this.group = group;
        this.sources = sources;
        this.edition = edition;
        this.container = container;
        this.fullSize = fullSize;
        this.resolutionDefinition = resolutionDefinition;
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
        this.widescreen = widescreen;
        this.blackAndWhite = blackAndWhite;
        this.ddd = ddd;
        this.complete = complete;
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

    public Long getFullSize() {
        return this.fullSize;
    }

    public ResolutionDefinition getResolutionDefinition() {
        return this.resolutionDefinition;
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

    public static ReleaseNfo.Builder builder() {
        return new ReleaseNfo.Builder();
    }

    public static class Builder {
        private static final Logger logger = LoggerFactory.getLogger(ReleaseNfo.Builder.class);
        private String group;
        private Set<Source> sources;
        private Edition edition;
        private Container container;
        private Long fullSize;
        private ResolutionDefinition resolutionDefinition;
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
        private Boolean widescreen;
        private Boolean blackAndWhite;
        private Boolean ddd;
        private Boolean complete;

        private Builder() {
        }

        public ReleaseNfo.Builder group(String group) {
            if (StringUtils.isEmpty(group)) {
                return this;
            } else if (this.group != null && !this.group.equals(group)) {
                logger.warn(String.format("Multiple but different group given! ('%s', '%s')", this.group, group));
            } else {
                this.group = group;
            }
            return this;
        }

        public ReleaseNfo.Builder sources(Set<Source> sources) {
            if (sources == null || sources.isEmpty()) {
                return this;
            } else if (this.sources == null) {
                this.sources = new HashSet<>(sources);
            } else {
                this.sources.addAll(sources);
            }
            return this;
        }

        public ReleaseNfo.Builder edition(Edition edition) {
            if (edition == null) {
                return this;
            } else if (this.edition != null && !this.edition.equals(edition)) {
                logger.warn(String.format("Multiple but different editions given! ('%s', '%s')", this.edition, edition));
            } else {
                this.edition = edition;
            }
            return this;
        }

        public ReleaseNfo.Builder container(Container container) {
            if (container == null) {
                return this;
            } else if (this.container != null && !this.container.equals(container)) {
                logger.warn(String.format("Multiple but different containers given! ('%s', '%s')", this.container, container));
            } else {
                this.container = container;
            }
            return this;
        }

        public ReleaseNfo.Builder fullSize(Long fullSize) {
            if (fullSize == null || fullSize == 0L) {
                return this;
            } else if (this.fullSize != null && !this.fullSize.equals(fullSize)) {
                logger.warn(String.format("Multiple but different full-size given! ('%s', '%s')", this.fullSize, fullSize));
            } else {
                this.fullSize = fullSize;
            }
            return this;
        }

        public ReleaseNfo.Builder resolutionDefinition(ResolutionDefinition resolutionDefinition) {
            if (resolutionDefinition == null) {
                return this;
            } else if (this.resolutionDefinition != null && !this.resolutionDefinition.equals(resolutionDefinition)) {
                logger.warn(String.format("Multiple but different resolution-definition given! ('%s', '%s')", this.resolutionDefinition, resolutionDefinition));
            } else {
                this.resolutionDefinition = resolutionDefinition;
            }
            return this;
        }

        public ReleaseNfo.Builder resolution(Resolution resolution) {
            if (resolution == null) {
                return this;
            } else if (this.resolution != null && !this.resolution.equals(resolution)) {
                logger.warn(String.format("Multiple but different resolutions given! ('%s', '%s')", this.resolution, resolution));
            } else {
                this.resolution = resolution;
            }
            return this;
        }

        public ReleaseNfo.Builder scanning(Scanning scanning) {
            if (scanning == null) {
                return this;
            } else if (this.scanning != null && !this.scanning.equals(scanning)) {
                logger.warn(String.format("Multiple but different scannings given! ('%s', '%s')", this.scanning, scanning));
            } else {
                this.scanning = scanning;
            }
            return this;
        }

        public ReleaseNfo.Builder videoCodec(VideoCodec videoCodec) {
            if (videoCodec == null) {
                return this;
            } else if (this.videoCodec != null && !this.videoCodec.equals(videoCodec)) {
                logger.warn(String.format("Multiple but different video-codecs given! ('%s', '%s')", this.videoCodec, videoCodec));
            } else {
                this.videoCodec = videoCodec;
            }
            return this;
        }

        public ReleaseNfo.Builder colorCodec(ColorCodec colorCodec) {
            if (colorCodec == null) {
                return this;
            } else if (this.colorCodec != null && !this.colorCodec.equals(colorCodec)) {
                logger.warn(String.format("Multiple but different color-codecs given! ('%s', '%s')", this.colorCodec, colorCodec));
            } else {
                this.colorCodec = colorCodec;
            }
            return this;
        }

        public ReleaseNfo.Builder hdr(Boolean hdr) {
            if (hdr == null) {
                return this;
            } else if (this.hdr != null && !this.hdr.equals(hdr)) {
                logger.warn(String.format("Multiple but different HDR indications given! ('%s', '%s')", this.hdr, hdr));
            } else {
                this.hdr = hdr;
            }
            return this;
        }

        public ReleaseNfo.Builder audioCodecs(Set<AudioCodec> audioCodecs) {
            if (audioCodecs == null || audioCodecs.isEmpty()) {
                return this;
            } else if (this.audioCodecs == null) {
                this.audioCodecs = new HashSet<>(audioCodecs);
            } else {
                this.audioCodecs.addAll(audioCodecs);
            }
            return this;
        }

        public ReleaseNfo.Builder audioChannels(Set<String> audioChannels) {
            if (audioChannels == null || audioChannels.isEmpty()) {
                return this;
            } else if (this.audioChannels == null) {
                this.audioChannels = new HashSet<>(audioChannels);
            } else {
                this.audioChannels.addAll(audioChannels);
            }
            return this;
        }

        public ReleaseNfo.Builder audioLanguages(Set<Language> audioLanguages) {
            if (audioLanguages == null || audioLanguages.isEmpty()) {
                return this;
            } else if (this.audioLanguages == null) {
                this.audioLanguages = new HashSet<>(audioLanguages);
            } else {
                this.audioLanguages.addAll(audioLanguages);
            }
            return this;
        }

        public ReleaseNfo.Builder audioLanguage(Language audioLanguage) {
            if (audioLanguage == null) {
                return this;
            }
            return this.audioLanguages(Collections.singleton(audioLanguage));
        }

        public ReleaseNfo.Builder subtitleLanguages(Set<Language> subtitleLanguages) {
            if (subtitleLanguages == null || subtitleLanguages.isEmpty()) {
                return this;
            } else if (this.subtitleLanguages == null) {
                this.subtitleLanguages = new HashSet<>(subtitleLanguages);
            } else {
                this.subtitleLanguages.addAll(subtitleLanguages);
            }
            return this;
        }

        public ReleaseNfo.Builder hardcoded(Boolean hardcoded) {
            if (hardcoded == null) {
                return this;
            } else if (this.hardcoded != null && !this.hardcoded.equals(hardcoded)) {
                logger.warn(String.format("Multiple but different hardcoded indications given! ('%s', '%s')", this.hardcoded, hardcoded));
            } else {
                this.hardcoded = hardcoded;
            }
            return this;
        }

        public ReleaseNfo.Builder widescreen(Boolean widescreen) {
            if (widescreen == null) {
                return this;
            } else if (this.widescreen != null && !this.widescreen.equals(widescreen)) {
                logger.warn(String.format("Multiple but different widescreen indications given! ('%s', '%s')", this.widescreen, widescreen));
            } else {
                this.widescreen = widescreen;
            }
            return this;
        }

        public ReleaseNfo.Builder blackAndWhite(Boolean blackAndWhite) {
            if (blackAndWhite == null) {
                return this;
            } else if (this.blackAndWhite != null && !this.blackAndWhite.equals(blackAndWhite)) {
                logger.warn(String.format("Multiple but different Black&White indications given! ('%s', '%s')", this.blackAndWhite, blackAndWhite));
            } else {
                this.blackAndWhite = blackAndWhite;
            }
            return this;
        }

        public ReleaseNfo.Builder ddd(Boolean ddd) {
            if (ddd == null) {
                return this;
            } else if (this.ddd != null && !this.ddd.equals(ddd)) {
                logger.warn(String.format("Multiple but different 3D indications given! ('%s', '%s')", this.ddd, ddd));
            } else {
                this.ddd = ddd;
            }
            return this;
        }

        public ReleaseNfo.Builder complete(Boolean complete) {
            if (complete == null) {
                return this;
            } else if (this.complete != null && !this.complete.equals(complete)) {
                logger.warn(String.format("Multiple but different Complete indications given! ('%s', '%s')", this.complete, complete));
            } else {
                this.complete = complete;
            }
            return this;
        }

        public ReleaseNfo build() {
            return new ReleaseNfo(this.group, this.sources, this.edition, this.container,
                    this.fullSize, this.resolutionDefinition, this.resolution, this.scanning, this.videoCodec,
                    this.colorCodec, this.hdr, this.audioCodecs, this.audioChannels, this.audioLanguages,
                    this.subtitleLanguages, this.hardcoded, this.widescreen, this.blackAndWhite, this.ddd, this.complete);
        }

        public ReleaseNfo build(ReleaseNfo... nfos) {
            for (ReleaseNfo nfo : nfos) {
                if (nfo == null) {
                    continue;
                }
                this
                        .group(nfo.group)
                        .sources(nfo.sources)
                        .edition(nfo.edition)
                        .container(nfo.container)
                        .fullSize(nfo.fullSize)
                        .resolutionDefinition(nfo.resolutionDefinition)
                        .resolution(nfo.resolution)
                        .scanning(nfo.scanning)
                        .videoCodec(nfo.videoCodec)
                        .colorCodec(nfo.colorCodec)
                        .hdr(nfo.hdr)
                        .audioCodecs(nfo.audioCodecs)
                        .audioChannels(nfo.audioChannels)
                        .audioLanguages(nfo.audioLanguages)
                        .subtitleLanguages(nfo.subtitleLanguages)
                        .hardcoded(nfo.hardcoded)
                        .widescreen(nfo.widescreen)
                        .blackAndWhite(nfo.blackAndWhite)
                        .ddd(nfo.ddd)
                        .complete(nfo.complete);
            }
            return this.build();
        }
    }
}
