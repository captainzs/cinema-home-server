package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.tracker.ncore.conf.NcoreAccountProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;

@EnableRetry
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PageLoader.class, LoginService.class, UrlMaker.class, NcoreAccountProperties.class})
public class PageLoaderIT {
    @Autowired
    private PageLoader loader;
    @MockBean
    private LoginService loginService;

    @Test(expected = IOException.class)
    public void givenNonExistingPage_whenLoad_assertPageLoadingRetries() throws IOException {
        try {
            this.loader.load(new URL("http://www.cinema.home.nonexistingpageformytest.tv"));
        } finally {
            Mockito.verify(this.loginService, Mockito.times(10)).getCookies();
        }
    }
}
