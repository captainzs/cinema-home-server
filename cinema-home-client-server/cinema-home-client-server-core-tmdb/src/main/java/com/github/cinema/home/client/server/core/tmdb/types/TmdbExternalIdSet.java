package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import org.springframework.util.StringUtils;

public class TmdbExternalIdSet {
    private final String imdbId;
    private final Long tvdbId;

    @JsonCreator
    private TmdbExternalIdSet(
            @JsonProperty("imdb_id") String imdbId,
            @JsonProperty("tvdb_id") Long tvdbId) {
        this.imdbId = imdbId;
        this.tvdbId = tvdbId;
    }

    public ImdbId getImdbId() {
        return !StringUtils.isEmpty(this.imdbId) ? ImdbId.of(this.imdbId) : null;
    }

    public TvdbId getTvdbId() {
        return this.tvdbId != null ? TvdbId.of(this.tvdbId) : null;
    }
}
