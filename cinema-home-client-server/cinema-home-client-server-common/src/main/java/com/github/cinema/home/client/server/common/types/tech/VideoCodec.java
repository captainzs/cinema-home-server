package com.github.cinema.home.client.server.common.types.tech;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VideoCodec {
    DIVX("DivX"),
    XVID("XviD"),
    MPEG2("MPEG2", "MPEG-2", "H.262", "H262"),
    MPEG4("MPEG4", "MPEG-4"),
    X264("H.264", "H264", "AVC", "x.264", "x264"),
    MVC("MVC"),
    X265("H.265", "H265", "HEVC", "x265"),
    VC1("VC-1", "VC1"),
    VP9("VP9");

    private static final Logger logger = LoggerFactory.getLogger(VideoCodec.class);

    private final String[] synonyms;

    VideoCodec(String... synonyms) {
        this.synonyms = synonyms;
    }

    public static Optional<VideoCodec> of(String codecName) {
        for (VideoCodec codec : VideoCodec.values()) {
            if (Arrays.stream(codec.synonyms).anyMatch(v -> v.toLowerCase().equals(codecName.toLowerCase()))) {
                return Optional.of(codec);
            }
        }
        logger.warn(String.format("Video-codec is not defined for %s!", codecName));
        return Optional.empty();
    }

    public static List<String> allSynonyms() {
        return Arrays.stream(VideoCodec.values())
                .map(c -> c.synonyms)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
