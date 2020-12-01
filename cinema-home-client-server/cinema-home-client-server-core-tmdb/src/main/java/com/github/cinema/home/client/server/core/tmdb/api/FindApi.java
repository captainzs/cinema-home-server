package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbFindResult;
import com.github.cinema.home.client.server.core.tmdb.config.TmdbAccountProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FindApi {
    private final TmdbAccountProperties properties;
    private final RestTemplate template;

    @Autowired
    public FindApi(TmdbAccountProperties properties, RestTemplate template) {
        this.properties = properties;
        this.template = template;
    }

    public TmdbFindResult requestMedias(ImdbId id) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("find")
                .path(id.strValue())
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("external_source", "imdb_id");
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbFindResult>() {
                }).getBody();
    }

    public TmdbFindResult requestMedias(TvdbId id) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("find")
                .path(String.valueOf(id))
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
                .queryParam("external_source", "tvdb_id");
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TmdbFindResult>() {
                }).getBody();
    }
}
