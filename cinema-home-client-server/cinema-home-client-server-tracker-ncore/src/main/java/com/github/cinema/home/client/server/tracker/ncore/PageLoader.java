package com.github.cinema.home.client.server.tracker.ncore;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class PageLoader {
    private final LoginService loginService;

    @Autowired
    public PageLoader(LoginService loginService) {
        this.loginService = loginService;
    }

    @Retryable(value = IOException.class, maxAttempts = 10, backoff = @Backoff(random = true, delay = 500, maxDelay = 1000))
    public Document load(URL pageUrl) throws IOException {
        Connection connection = Jsoup.connect(pageUrl.toString()).cookies(this.loginService.getCookies());
        return Jsoup.parse(connection.execute().body());
    }
}
