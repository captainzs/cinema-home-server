package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbSeason;
import com.github.cinema.home.client.server.core.tmdb.config.TmdbAccountProperties;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@Component
public class DetailsApi {
    private final TmdbAccountProperties properties;
    private final RestTemplate template;

    @Autowired
    public DetailsApi(TmdbAccountProperties properties, RestTemplate template) {
        this.properties = properties;
        this.template = template;
    }

    public TmdbMovie requestMovie(TmdbId id, Locale locale) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("movie")
                .path(String.valueOf(id))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", locale.getLanguage())
                .queryParam("append_to_response", "external_ids,videos,credits,images")
                .queryParam("include_image_language", locale.getLanguage() + ",en,null");
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbMovie>() {
                }).getBody();
    }

    public TmdbShow requestShow(TmdbId id, Locale locale) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("tv")
                .path(String.valueOf(id))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", locale.getLanguage())
                .queryParam("append_to_response", "external_ids,videos,credits,images")
                .queryParam("include_image_language", locale.getLanguage() + ",en,null");
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbShow>() {
                }).getBody();
    }

    public TmdbSeason requestSeason(TmdbId showId, int seasonNumber, Locale locale) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("tv")
                .pathSegment(String.valueOf(showId))
                .pathSegment("season")
                .path(String.valueOf(seasonNumber))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("append_to_response", "videos,images")
                .queryParam("include_image_language", locale.getLanguage() + ",en,null");
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbSeason>() {
                }).getBody();
    }
}
