package com.github.cinema.home.client.server.torrent.client.transmission.types;

public class InvalidCsrfException extends Exception {

    public InvalidCsrfException(String message) {
        super(message);
    }

    public InvalidCsrfException(String message, Throwable cause) {
        super(message, cause);
    }
}
