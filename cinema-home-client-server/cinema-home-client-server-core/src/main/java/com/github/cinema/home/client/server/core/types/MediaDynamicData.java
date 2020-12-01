package com.github.cinema.home.client.server.core.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class MediaDynamicData {
    private final boolean isFavored;

    private MediaDynamicData(boolean isFavored) {
        this.isFavored = isFavored;
    }

    @JsonProperty("isFavored")
    public boolean isFavored() {
        return this.isFavored;
    }

    public static MediaDynamicData.Builder builder() {
        return new MediaDynamicData.Builder();
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class Builder {
        private Boolean isFavored;

        private Builder() {
        }

        public MediaDynamicData.Builder isFavored(boolean isFavored) {
            this.isFavored = isFavored;
            return this;
        }

        public MediaDynamicData build() {
            return new MediaDynamicData(this.isFavored);
        }
    }
}
