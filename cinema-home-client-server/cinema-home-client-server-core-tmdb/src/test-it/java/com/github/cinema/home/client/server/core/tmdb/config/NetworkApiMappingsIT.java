package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.core.tmdb.TestContext;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbCompany;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class, TmdbAccountProperties.class})
public class NetworkApiMappingsIT {
    @Autowired
    private TmdbAccountProperties properties;
    @SpyBean
    private RestTemplate template;

    @Test
    public void givenApiMapping_whenDownloadAllMappedNetworkDetails_assertAllHasTheSameNameAsInApiConfig() {
        for (NetworkGroup group : NetworkGroup.values()) {
            for (TmdbCompany network : NetworkApiMappings.of(group)) {
                TmdbCompany details = this.getNetworkDetails(network.getId());
                Assert.assertEquals(network.getId(), details.getId());
                Assert.assertEquals(network.getName(), details.getName());
            }
        }
    }

    private TmdbCompany getNetworkDetails(int id) {
        try {
            URI uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("api.themoviedb.org")
                    .pathSegment("3")
                    .pathSegment("network")
                    .path(String.valueOf(id))
                    .queryParam("api_key", this.properties.getApiKey())
                    .queryParam("language", "en")
                    .build().toUri();
            return this.template.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<TmdbCompany>() {
                    }).getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException(String.format("Network with id: '%s' failed!", id), e);
        }
    }
}
