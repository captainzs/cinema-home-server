package com.github.cinema.home.client.server.core.tmdb.utils;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

// TODO write test if these url-s are up-to-date. Test with configuration api
public class WebUrlMaker {
    public static Optional<URL> getTmdbImageUrl(String posterId, String size) {
        if (StringUtils.isEmpty(posterId) || StringUtils.isEmpty(size)) {
            return Optional.empty();
        }
        try {
            return Optional.of(UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("image.tmdb.org")
                    .pathSegment("t", "p")
                    .pathSegment(size)
                    .path(posterId)
                    .build().toUri().toURL());
        } catch (MalformedURLException e) {
            throw new InvalidConfigurationException(String.format("Can not create Tmdb image url with poster-id: '%s' and size: '%s'", posterId, size));
        }
    }

    public static Optional<URL> getTmdbYoutubeUrl(String youtubeKey) {
        if (StringUtils.isEmpty(youtubeKey)) {
            return Optional.empty();
        }
        try {
            return Optional.of(UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("www.youtube.com")
                    .path("watch")
                    .queryParam("v", youtubeKey)
                    .build().toUri().toURL());
        } catch (MalformedURLException e) {
            throw new InvalidConfigurationException(String.format("Can not create Tmdb youtube url with youtubeKey: '%s'", youtubeKey));
        }
    }
}
