package com.github.cinema.home.client.server.core.comm.conf;

import com.github.cinema.home.client.server.common.types.media.ImdbId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToImdbIdConverter implements Converter<String, ImdbId> {
    @Override
    public ImdbId convert(@NonNull String s) {
        return ImdbId.of(s);
    }
}
