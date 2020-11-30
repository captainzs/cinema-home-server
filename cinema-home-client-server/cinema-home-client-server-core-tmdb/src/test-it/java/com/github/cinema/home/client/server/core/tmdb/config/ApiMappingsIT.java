package com.github.cinema.home.client.server.core.tmdb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.types.media.MediaType;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbGenre;
import com.github.cinema.home.client.server.core.tmdb.TestContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class, TmdbAccountProperties.class})
public class ApiMappingsIT {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TmdbAccountProperties properties;
    @SpyBean
    private RestTemplate template;

    @Test
    public void givenConstantApiConfig_whenDownloadCurrentApiConfig_assertAllMappedToCommonGenres() throws JSONException {
        Set<TmdbGenre> liveApiMap = this.convertJsonToSet(this.getGenresResponse(MediaType.MOVIE));
        liveApiMap.addAll(this.convertJsonToSet(this.getGenresResponse(MediaType.SHOW)));
        for (TmdbGenre liveGenre : liveApiMap) {
            Set<Genre> mapped = ApiMappings.toGenres(Collections.singleton(liveGenre));
            Assert.assertNotNull(mapped);
            Assert.assertFalse(mapped.isEmpty());
        }
    }

    @Test
    public void givenConstantApiConfig_whenDownloadCurrentApiConfig_assertNoDeprecatedTmdbGenreConfigured() throws JSONException {
        Set<TmdbGenre> liveApiMap = this.convertJsonToSet(this.getGenresResponse(MediaType.MOVIE));
        liveApiMap.addAll(this.convertJsonToSet(this.getGenresResponse(MediaType.SHOW)));
        for (Genre genre : Genre.values()) {
            Set<TmdbGenre> configured = ApiMappings.of(genre);
            Assert.assertTrue(liveApiMap.containsAll(configured));
        }
    }

    private JSONArray getGenresResponse(MediaType type) throws JSONException {
        URI builder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.themoviedb.org")
                .pathSegment("3")
                .pathSegment("genre")
                .pathSegment(type == MediaType.MOVIE ? "movie" : "tv")
                .path("list")
                .queryParam("api_key", this.properties.getApiKey())
                .queryParam("language", "en")
                .build().toUri();
        return new JSONObject(this.template.getForEntity(builder, String.class).getBody()).getJSONArray("genres");
    }

    private Set<TmdbGenre> convertJsonToSet(JSONArray json) throws JSONException {
        Set<TmdbGenre> result = new HashSet<>(json.length());
        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            result.add(TmdbGenre.of(obj.getInt("id"), obj.getString("name")));
        }
        return result;
    }
}
