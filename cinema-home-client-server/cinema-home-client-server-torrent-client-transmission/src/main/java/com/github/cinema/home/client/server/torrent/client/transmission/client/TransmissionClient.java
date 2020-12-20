package com.github.cinema.home.client.server.torrent.client.transmission.client;

import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.torrent.client.transmission.config.TransmissionAccountProperties;
import com.github.cinema.home.client.server.torrent.client.transmission.config.TransmissionProperties;
import com.github.cinema.home.client.server.torrent.client.transmission.types.InvalidCsrfException;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Request;
import com.github.cinema.home.client.server.torrent.client.transmission.types.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

@Component
public class TransmissionClient {
    private final CsrfRetryRestTemplate restTemplate;
    private final TransmissionAccountProperties account;
    private final TransmissionProperties host;

    @Autowired
    public TransmissionClient(CsrfRetryRestTemplate restTemplate, TransmissionAccountProperties account,
                              TransmissionProperties host) {
        this.restTemplate = restTemplate;
        this.account = account;
        this.host = host;
    }

    public Response doRequest(Request request) throws ServiceErrorException {
        try {
            return this.restTemplate.csrfExchange(
                    this.host.getRpcUrl(),
                    HttpMethod.POST,
                    new HttpEntity<>(request, this.authenticationHeaders()),
                    new ParameterizedTypeReference<Response>() {
                    }).getBody();
        } catch (InvalidCsrfException e) {
            throw new ServiceErrorException("Can not reach transmission service!", e);
        }
    }

    private HttpHeaders authenticationHeaders() {
        String username = this.account.getUsername();
        String password = this.account.getPassword();
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            return new HttpHeaders();
        }
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            this.set("Authorization", "Basic " + new String(encodedAuth));
        }};
    }
}
