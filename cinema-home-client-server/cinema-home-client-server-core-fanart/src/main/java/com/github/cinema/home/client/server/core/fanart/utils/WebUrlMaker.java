package com.github.cinema.home.client.server.core.fanart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class WebUrlMaker {
    private static final Logger logger = LoggerFactory.getLogger(WebUrlMaker.class);

    public static Optional<URL> getFanartImageUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return Optional.empty();
        }
        try {
            return Optional.of(new URL(url));
        } catch (MalformedURLException e) {
            logger.error("Creating FanArt url failed!", e);
            return Optional.empty();
        }
    }
}
