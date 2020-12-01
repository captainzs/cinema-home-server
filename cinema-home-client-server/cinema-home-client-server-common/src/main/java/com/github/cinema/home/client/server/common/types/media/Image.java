package com.github.cinema.home.client.server.common.types.media;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image {
    private final URL url;
    private final String iso639Id;

    public static Image clear(URL url) {
        return Image.locale(url, null);
    }

    public static Image locale(URL url, Locale locale) {
        if (url == null) {
            throw new IllegalArgumentException("Can not create local specific image with empty url!");
        }
        return new Image(url, locale == null ? null : locale.getLanguage());
    }

    private Image(URL url, String iso639Id) {
        this.url = url;
        this.iso639Id = iso639Id;
    }

    public URL getUrl() {
        return this.url;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    @Override
    public String toString() {
        return this.url.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return this.url.equals(image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url);
    }
}
