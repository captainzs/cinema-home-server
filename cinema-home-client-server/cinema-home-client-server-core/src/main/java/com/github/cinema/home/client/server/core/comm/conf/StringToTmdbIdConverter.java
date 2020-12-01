package com.github.cinema.home.client.server.core.comm.conf;

import com.github.cinema.home.client.server.common.types.media.TmdbId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToTmdbIdConverter implements Converter<String, TmdbId> {
    @Override
    public TmdbId convert(@NonNull String s) {
        return TmdbId.of(Integer.parseInt(s));
    }
}
