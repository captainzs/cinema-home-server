package com.github.cinema.home.client.server.common.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.cinema.home.client.server.common.types.media.Genre;

import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(builder = SearchFilter.Builder.class)
public class SearchFilter {
    private final String subText;
    private final Set<Genre> genres;
    private final boolean mandatory3d;
    private final SortBy sortBy;
    private final SortOrder sortOrder;

    private SearchFilter(String subText, Set<Genre> genres, boolean mandatory3d, SortBy sortBy, SortOrder sortOrder) {
        this.subText = subText;
        this.genres = genres;
        this.mandatory3d = mandatory3d;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public String getSubText() {
        return this.subText;
    }

    public Set<Genre> getGenres() {
        return this.genres;
    }

    public boolean getMandatory3d() {
        return this.mandatory3d;
    }

    public SortBy getSortBy() {
        return this.sortBy;
    }

    public SortOrder getSortOrder() {
        return this.sortOrder;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "subText='" + this.subText + '\'' +
                ", genres=" + this.genres +
                ", 3d=" + this.mandatory3d +
                ", sortBy=" + this.sortBy +
                ", sortOrder=" + this.sortOrder +
                '}';
    }

    public static SearchFilter.Builder builder() {
        return new SearchFilter.Builder();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class Builder {
        private String subText = "";
        private Set<Genre> genres = new HashSet<>();
        private boolean mandatory3d = false;
        private SortBy sortBy;
        private SortOrder sortOrder;

        private Builder() {
        }

        public SearchFilter.Builder subText(String subText) {
            if (subText == null) {
                return this;
            }
            this.subText = subText;
            return this;
        }

        public SearchFilter.Builder genres(Set<Genre> genres) {
            if (genres == null) {
                return this;
            }
            this.genres = genres;
            return this;
        }

        public SearchFilter.Builder mandatory3d(boolean mandatory3d) {
            this.mandatory3d = mandatory3d;
            return this;
        }

        public SearchFilter.Builder sortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public SearchFilter.Builder sortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public SearchFilter build() {
            return new SearchFilter(this.subText, this.genres, this.mandatory3d, this.sortBy, this.sortOrder);
        }
    }
}
