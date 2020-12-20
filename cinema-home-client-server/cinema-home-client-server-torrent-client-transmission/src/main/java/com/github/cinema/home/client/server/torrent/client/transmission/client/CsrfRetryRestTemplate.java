package com.github.cinema.home.client.server.torrent.client.transmission.client;

import com.github.cinema.home.client.server.torrent.client.transmission.types.InvalidCsrfException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class CsrfRetryRestTemplate extends RestTemplate {
    private static final String CSRF_HEADER_KEY = "X-Transmission-Session-Id";
    private final RetryTemplate retryTemplate;

    public CsrfRetryRestTemplate(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    public <T> ResponseEntity<T> csrfExchange(URI url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
                                              ParameterizedTypeReference<T> responseType)
            throws InvalidCsrfException {
        return this.retryTemplate.execute(context -> {
            HttpEntity<?> finalRequest = requestEntity;
            if (finalRequest == null) {
                finalRequest = new HttpEntity<>(null, new HttpHeaders());
            }
            if (context.hasAttribute(CSRF_HEADER_KEY)) {
                HttpHeaders newHeaders = HttpHeaders.writableHttpHeaders(finalRequest.getHeaders());
                newHeaders.set(CSRF_HEADER_KEY, String.valueOf(context.getAttribute(CSRF_HEADER_KEY)));
                finalRequest = new HttpEntity<>(finalRequest.getBody(), newHeaders);
            }
            try {
                return CsrfRetryRestTemplate.super.exchange(url, method, finalRequest, responseType);
            } catch (HttpStatusCodeException e) {
                if (e.getStatusCode() == HttpStatus.CONFLICT) {
                    HttpHeaders headers = e.getResponseHeaders();
                    if (headers != null && headers.containsKey(CSRF_HEADER_KEY)) {
                        List<String> csrfTokens = headers.get(CSRF_HEADER_KEY);
                        if (csrfTokens != null && !csrfTokens.isEmpty()) {
                            context.setAttribute(CSRF_HEADER_KEY, csrfTokens.get(0));
                            throw new InvalidCsrfException("CSRF token is invalid. Retrying request with renewed token!");
                        }
                    }
                }
                throw e;
            }
        });
    }
}
