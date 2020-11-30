package com.github.cinema.home.client.server.common;

import com.github.cinema.home.client.server.common.types.media.MediaNfo;

@FunctionalInterface
public interface ExtentDecider {
    boolean hasEnoughProperties(MediaNfo media);
}
