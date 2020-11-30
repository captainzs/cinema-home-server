package com.github.cinema.home.client.server.tracker.ncore.conf;

import com.github.cinema.home.client.server.common.exceptions.InvalidConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NcoreAccountProperties {
    private final String username;
    private final String password;

    @Autowired
    public NcoreAccountProperties() {
        String username = System.getenv("NCORE_USERNAME");
        String password = System.getenv("NCORE_PASSWORD");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new InvalidConfigurationException("Ncore account configuration is not given correctly!)");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
