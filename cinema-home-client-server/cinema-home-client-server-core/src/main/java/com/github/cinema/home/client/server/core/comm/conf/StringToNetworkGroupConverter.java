package com.github.cinema.home.client.server.core.comm.conf;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToNetworkGroupConverter implements Converter<String, NetworkGroup> {
    @Override
    public NetworkGroup convert(@NonNull String s) {
        return NetworkGroup.of(Integer.parseInt(s))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Network not exists with id: '%s'", s)));
    }
}
