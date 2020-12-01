package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TmdbSearchResult<T> {
    private final int page;
    private final int totalResults;
    private final int totalPages;
    private final List<T> results;

    @JsonCreator
    private TmdbSearchResult(
            @JsonProperty("page") int page,
            @JsonProperty("total_results") int totalResults,
            @JsonProperty("total_pages") int totalPages,
            @JsonProperty("results") List<T> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return this.page;
    }

    public int getTotalResults() {
        return this.totalResults;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public List<T> getResults() {
        return this.results;
    }

    @Override
    public String toString() {
        return "TmdbSearchResult{" +
                "page=" + this.page +
                ", totalResults=" + this.totalResults +
                ", totalPages=" + this.totalPages +
                ", results=" + this.results +
                '}';
    }
}
