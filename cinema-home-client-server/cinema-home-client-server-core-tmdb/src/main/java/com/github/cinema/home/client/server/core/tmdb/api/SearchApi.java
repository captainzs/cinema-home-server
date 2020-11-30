package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
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

@Component
public class SearchApi {
    private final TmdbAccountProperties properties;
    private final RestTemplate template;

    @Autowired
    public SearchApi(TmdbAccountProperties properties, RestTemplate template) {
        this.properties = properties;
        this.template = template;
    }

    public TmdbSearchResult<TmdbMovie> requestMovies(String query, Integer releaseYear, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("search")
                .path("movie")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page)
                .queryParam("include_adult", false)
                .queryParam("query", query);
        if (releaseYear != null) {
            urlBuilder.queryParam("year", releaseYear);
        }
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbMovie>>() {
                }).getBody();
    }

    public TmdbSearchResult<TmdbShow> requestShows(String query, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("search")
                .path("tv")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page)
                .queryParam("include_adult", false)
                .queryParam("query", query);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbShow>>() {
                }).getBody();
    }
}
