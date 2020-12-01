package com.github.cinema.home.client.server.core.fanart;

import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import com.github.cinema.home.client.server.core.fanart.config.FanartAccountProperties;
import com.github.cinema.home.client.server.core.fanart.types.FanartMovie;
import com.github.cinema.home.client.server.core.fanart.types.FanartShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FanartApiService {
    private final RestTemplate template;
    private final FanartAccountProperties properties;

    @Autowired
    public FanartApiService(RestTemplate template, FanartAccountProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    FanartMovie requestMovieArts(ImdbId imdbId) {
        return this.requestMovieArts(String.valueOf(imdbId));
    }

    FanartMovie requestMovieArts(TmdbId tmdbId) {
        return this.requestMovieArts(String.valueOf(tmdbId));
    }

    private FanartMovie requestMovieArts(String id) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("http")
                .host("webservice.fanart.tv")
                .pathSegment("v3")
                .pathSegment("movies")
                .path(id)
                .queryParam("api_key", this.properties.getApiKey());
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<FanartMovie>() {
                }).getBody();
    }

    FanartShow requestShowArts(TvdbId tvdbId) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance().scheme("http")
                .host("webservice.fanart.tv")
                .pathSegment("v3")
                .pathSegment("tv")
                .path(String.valueOf(tvdbId))
                .queryParam("api_key", this.properties.getApiKey());
        return this.template.exchange(
                urlBuilder.build().toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<FanartShow>() {
                }).getBody();
    }
}
