package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Component
public class TmdbAccountProperties {
    private final static String API_KEY_ENV_VAR = "TMDB_API_KEY";
    private String apiKey;

    @PostConstruct
    private void loadApiKey() {
        String apiKey = System.getenv(API_KEY_ENV_VAR);
        if (StringUtils.isEmpty(apiKey)) {
            throw new InvalidConfigurationException("Tmdb api key configuration is not given correctly!)");
        }
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }
}
