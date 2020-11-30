package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.SortBy;
import com.github.cinema.home.client.server.common.types.SortOrder;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaType;
import com.github.cinema.home.client.server.tracker.ncore.conf.ApiMappings;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class UrlMaker {
    public URL logInUrl() {
        try {
            return UriComponentsBuilder.newInstance().scheme("https").host("ncore.cc").path("login.php")
                    .build().toUri().toURL();
        } catch (MalformedURLException e) {
            throw new InvalidConfigurationException("LogIn url is incorrectly configured!", e);
        }
    }

    public URL recommendedUrl() {
        try {
            return UriComponentsBuilder.newInstance().scheme("https").host("ncore.cc").path("recommended.php")
                    .build().toUri().toURL();
        } catch (MalformedURLException e) {
            throw new InvalidConfigurationException("Recommended-page url is incorrectly configured!", e);
        }
    }

    public URL searchUrl(SearchFilter filter, int page, MediaType type) throws InvalidArgumentException {
        if (page < 1) {
            page = 1;
        }
        try {
            String tags = filter.getGenres().stream().map(ApiMappings::of).collect(Collectors.joining(","));
            if (filter.getMandatory3d() == Boolean.TRUE) {
                tags = tags.concat(",3D");
            }
            return UriComponentsBuilder.newInstance().scheme("https").host("ncore.cc").path("torrents.php")
                    .queryParam("oldal", page)
                    .queryParam("miszerint", ApiMappings.of(filter.getSortBy()))
                    .queryParam("hogyan", ApiMappings.of(filter.getSortOrder()))
                    .queryParam("tipus", "kivalasztottak_kozott")
                    .queryParam("kivalasztott_tipus", String.join(",", ApiMappings.of(type)))
                    .queryParam("tags", tags)
                    .queryParam("mire", filter.getSubText().replace(" ", "+"))
                    .queryParam("miben", "name")
                    .build().toUri().toURL();
        } catch (MalformedURLException e) {
            throw new InvalidArgumentException(
                    String.format("Search-page url is malformed! Given filters '%s' or page '%d' or type '%s' is possibly invalid!", filter, page, type), e);
        }
    }

    public URL searchUrl(ImdbId id, int page, MediaType type) throws InvalidArgumentException {
        if (page < 1) {
            page = 1;
        }
        try {
            return UriComponentsBuilder.newInstance().scheme("https").host("ncore.cc").path("torrents.php")
                    .queryParam("oldal", page)
                    .queryParam("miszerint", ApiMappings.of(SortBy.POPULARITY))
                    .queryParam("hogyan", ApiMappings.of(SortOrder.DESCENDING))
                    .queryParam("tipus", "kivalasztottak_kozott")
                    .queryParam("kivalasztott_tipus", String.join(",", ApiMappings.of(type)))
                    .queryParam("mire", id)
                    .queryParam("miben", "imdb")
                    .build().toUri().toURL();
        } catch (MalformedURLException e) {
            throw new InvalidArgumentException(
                    String.format("Search-page url is malformed! Given imdb-id '%s' or page '%d' or type '%s' is possibly invalid!", id, page, type), e);
        }
    }

    public URL detailsUrl(String ncoreId) throws InvalidArgumentException {
        try {
            return UriComponentsBuilder.newInstance().scheme("https").host("ncore.cc").path("torrents.php")
                    .queryParam("action", "details")
                    .queryParam("amp;id", ncoreId)
                    .build().toUri().toURL();
        } catch (MalformedURLException e) {
            throw new InvalidArgumentException(String.format("Details-page url is malformed! Given ncore-id '%s' is possibly invalid!", ncoreId), e);
        }
    }
}
