package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import com.github.cinema.home.client.server.tracker.ncore.conf.NcoreAccountProperties;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginService {
    private static final String USERNAME_KEY = "nev";
    private static final String PASSWORD_KEY = "pass";
    private static final String REMEMBER_KEY = "ne_leptessen_ki";

    private static final String COOKIE_SESSION_KEY = "PHPSESSID";
    private static final String COOKIE_USERNAME_KEY = "nick";
    private static final String COOKIE_PASSWORD_KEY = "pass";

    private static final long LOGIN_INTERVAL_MS = 12L * 60L * 1000L;

    private final UrlMaker urlMaker;
    private final NcoreAccountProperties account;

    private ConcurrentHashMap<String, String> loginCookies;

    @Autowired
    public LoginService(UrlMaker urlMaker, NcoreAccountProperties account) {
        this.urlMaker = urlMaker;
        this.account = account;
    }

    @PostConstruct
    @Scheduled(fixedRate = LOGIN_INTERVAL_MS, initialDelay = LOGIN_INTERVAL_MS)
    private void logInAndCacheCookie() throws IOException {
        this.loginCookies = this.logIn();
    }

    public Map<String, String> getCookies() {
        return new HashMap<>(this.loginCookies);
    }

    private ConcurrentHashMap<String, String> logIn() throws IOException {
        Connection.Response res = Jsoup.connect(this.urlMaker.logInUrl().toString())
                .data(USERNAME_KEY, this.account.getUsername(), PASSWORD_KEY, this.account.getPassword(), REMEMBER_KEY, "1")
                .method(Connection.Method.POST)
                .execute();
        if (res.statusCode() != 200) {
            throw new InvalidConfigurationException("Ncore login failed!");
        }
        if (!(res.hasCookie(COOKIE_SESSION_KEY) && res.hasCookie(COOKIE_USERNAME_KEY) && res.hasCookie(COOKIE_PASSWORD_KEY)) ||
                !res.cookie(COOKIE_USERNAME_KEY).equals(this.account.getUsername())) {
            throw new InvalidConfigurationException("Ncore login failed due to invalid cookie response!");
        }
        return new ConcurrentHashMap<>(res.cookies());
    }
}
