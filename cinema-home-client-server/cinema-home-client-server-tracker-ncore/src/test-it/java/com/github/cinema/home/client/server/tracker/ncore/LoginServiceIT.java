package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.tracker.ncore.conf.NcoreAccountProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@EnableScheduling
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LoginService.class, UrlMaker.class, NcoreAccountProperties.class})
public class LoginServiceIT {
    @Autowired
    private LoginService cache;

    @Test
    public void givenValidAuthConfig_whenSpringCtxCreated_assertLoginCookiesAutoCached() {
        Assert.assertNotNull(this.cache.getCookies());
        Assert.assertTrue(this.cache.getCookies().containsKey("PHPSESSID"));
    }
}
