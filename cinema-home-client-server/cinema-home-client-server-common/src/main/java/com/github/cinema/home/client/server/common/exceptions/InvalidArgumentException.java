package com.github.cinema.home.client.server.common.exceptions;

public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
