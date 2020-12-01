package com.github.cinema.home.client.server.common.exceptions;

import java.io.IOException;

public class ServiceErrorException extends IOException {

    public ServiceErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
