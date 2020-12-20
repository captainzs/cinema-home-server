package com.github.cinema.home.client.server.torrent.client.transmission.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    private final String result;
    private final ResponseArguments arguments;

    @JsonCreator
    private Response(
            @JsonProperty("result") String result,
            @JsonProperty("arguments") ResponseArguments arguments) {
        this.result = result;
        this.arguments = arguments;
    }

    public boolean isSuccess() {
        return "success".equals(this.result);
    }

    public String getResult() {
        return this.result;
    }

    public ResponseArguments getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result='" + this.result + '\'' +
                ", arguments=" + this.arguments +
                '}';
    }
}
