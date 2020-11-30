package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.core.tmdb.types.TmdbSearchResult;
import com.github.cinema.home.client.server.core.tmdb.config.TmdbAccountProperties;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DiscoverApi {
    private final TmdbAccountProperties properties;
    private final RestTemplate template;

    @Autowired
    public DiscoverApi(TmdbAccountProperties properties, RestTemplate template) {
        this.properties = properties;
        this.template = template;
    }

    public TmdbSearchResult<TmdbShow> requestStreamedShows(Set<Integer> networkIds, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("discover")
                .path("tv")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page)
                .queryParam("sort_by", "popularity.desc")
                .queryParam("with_networks", networkIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining("|")))
                .queryParam("include_null_first_air_dates", false)
                .queryParam("include_adult", false)
                .queryParam("include_video", false);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbShow>>() {
                }).getBody();
    }
}
