package com.github.cinema.home.client.server.common.types.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocaleString {
    private final String str;
    private final String iso639Id;

    public static LocaleString unknown(String str) {
        return LocaleString.locale(str, null);
    }

    public static LocaleString locale(String str, Locale locale) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Can not create local specific string with empty string!");
        }
        return new LocaleString(str, locale == null ? null : locale.getLanguage());
    }

    private LocaleString(String str, String iso639Id) {
        this.str = str;
        this.iso639Id = iso639Id;
    }

    public String getStr() {
        return this.str;
    }

    public String getIso639Id() {
        return this.iso639Id;
    }

    @Override
    public String toString() {
        return this.str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        LocaleString that = (LocaleString) o;
        return this.str.equals(that.str) &&
                Objects.equals(this.iso639Id, that.iso639Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.str, this.iso639Id);
    }
}
