package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.types.media.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class ApiMappingsTest {
    @Test
    public void givenApiMappings_assertNoGenreUnmapped() {
        for (Genre genre : Genre.values()) {
            Assertions.assertDoesNotThrow(() -> ApiMappings.of(genre));
        }
    }

    @Test
    public void givenApiMappings_assertNoGenreMappedToNull() {
        for (Genre genre : Genre.values()) {
            Assert.assertNotNull(ApiMappings.of(genre));
        }
    }

    @Test
    public void givenNullGenreIds_whenMap_assertEmptySetReturned() {
        Assert.assertTrue(ApiMappings.toGenresByIds(null).isEmpty());
        Assert.assertTrue(ApiMappings.toGenresByIds(Collections.emptySet()).isEmpty());
    }

    @Test
    public void givenNullGenres_whenMap_assertEmptySetReturned() {
        Assert.assertTrue(ApiMappings.toGenres(null).isEmpty());
        Assert.assertTrue(ApiMappings.toGenres(Collections.emptySet()).isEmpty());
    }
}
