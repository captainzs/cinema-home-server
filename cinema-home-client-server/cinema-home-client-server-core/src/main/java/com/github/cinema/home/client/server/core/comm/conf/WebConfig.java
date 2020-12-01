package com.github.cinema.home.client.server.core.comm.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTmdbIdConverter());
        registry.addConverter(new StringToImdbIdConverter());
        registry.addConverter(new StringToNetworkGroupConverter());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

