package com.github.cinema.home.client.server.tracker.ncore.conf;

import com.github.cinema.home.client.server.common.utils.LocaleDetector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigs {
    @Bean
    public LocaleDetector localeDetector() {
        return new LocaleDetector();
    }
}
