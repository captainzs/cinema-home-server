package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video {
    private final String title;
    private final String iso639Id;
    private final URL url;
    private final VideoType type;

    private Video(String title, String iso639Id, URL url, VideoType type) {
        this.title = title;
        this.iso639Id = iso639Id;
        this.url = url;
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    public URL getUrl() {
        return this.url;
    }

    public VideoType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return this.url.equals(video.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url);
    }

    public static Video.Builder builder() {
        return new Video.Builder();
    }

    public static class Builder {
        private String title;
        private String iso639Id;
        private URL url;
        private VideoType type;

        private Builder() {
        }

        public Video.Builder title(String title) {
            if (StringUtils.isEmpty(title)) {
                return this;
            }
            this.title = title;
            return this;
        }

        public Video.Builder iso639Id(String iso639Id) {
            if (StringUtils.isEmpty(iso639Id)) {
                return this;
            }
            this.iso639Id = iso639Id;
            return this;
        }

        public Video.Builder url(URL url) {
            if (url == null) {
                return this;
            }
            this.url = url;
            return this;
        }

        public Video.Builder type(VideoType type) {
            if (type == null) {
                return this;
            }
            this.type = type;
            return this;
        }

        public Video build() {
            return new Video(this.title, this.iso639Id, this.url, this.type);
        }
    }
}
