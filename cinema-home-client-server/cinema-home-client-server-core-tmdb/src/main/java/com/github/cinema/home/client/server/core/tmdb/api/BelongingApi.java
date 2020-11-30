package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbSearchResult;
import com.github.cinema.home.client.server.core.tmdb.config.TmdbAccountProperties;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbCollectionResult;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BelongingApi {
    private final TmdbAccountProperties properties;
    private final RestTemplate template;

    @Autowired
    public BelongingApi(TmdbAccountProperties properties, RestTemplate template) {
        this.properties = properties;
        this.template = template;
    }

    public TmdbSearchResult<TmdbMovie> requestSimilarMoviesFor(TmdbId id, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("movie")
                .pathSegment(String.valueOf(id))
                .path("similar")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbMovie>>() {
                }).getBody();
    }

    public TmdbSearchResult<TmdbShow> requestSimilarShowsFor(TmdbId id, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("tv")
                .pathSegment(String.valueOf(id))
                .path("similar")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbShow>>() {
                }).getBody();
    }

    public TmdbSearchResult<TmdbMovie> requestRecommendedMoviesFor(TmdbId id, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("movie")
                .pathSegment(String.valueOf(id))
                .path("recommendations")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbMovie>>() {
                }).getBody();
    }

    public TmdbSearchResult<TmdbShow> requestRecommendedShowsFor(TmdbId id, int page) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("tv")
                .pathSegment(String.valueOf(id))
                .path("recommendations")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("page", page);
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSearchResult<TmdbShow>>() {
                }).getBody();
    }

    public TmdbCollectionResult<TmdbMovie> requestMovieCollection(TmdbId collectionId) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("collection")
                .path(String.valueOf(collectionId))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage());
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbCollectionResult<TmdbMovie>>() {
                }).getBody();
    }

    public TmdbCollectionResult<TmdbShow> requestShowCollection(TmdbId collectionId) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("collection")
                .path(String.valueOf(collectionId))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage());
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbCollectionResult<TmdbShow>>() {
                }).getBody();
    }
}
