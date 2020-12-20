package com.github.cinema.home.client.server.torrent.client.transmission.types;

import java.util.Map;

public class Request {
    private final Method method;
    private final Map<String, Object> arguments;

    private Request(Method method, Map<String, Object> arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public Method getMethod() {
        return this.method;
    }

    public Map<String, Object> getArguments() {
        return this.arguments;
    }

    public static Request.Builder builder() {
        return new Request.Builder();
    }

    public static class Builder {
        private Method method;
        private Map<String, Object> arguments;

        private Builder() {
        }

        public Request.Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Request.Builder arguments(Map<String, Object> arguments) {
            this.arguments = arguments;
            return this;
        }

        public Request build() {
            return new Request(this.method, this.arguments);
        }
    }
}
