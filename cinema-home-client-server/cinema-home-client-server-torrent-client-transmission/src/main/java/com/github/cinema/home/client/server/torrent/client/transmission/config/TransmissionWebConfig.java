package com.github.cinema.home.client.server.torrent.client.transmission.config;

import com.github.cinema.home.client.server.torrent.client.transmission.client.CsrfRetryRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class TransmissionWebConfig {
    @Bean
    @Primary
    public CsrfRetryRestTemplate csrfRestTemplate() {
        return new CsrfRetryRestTemplate(new RetryTemplate());
    }
}

