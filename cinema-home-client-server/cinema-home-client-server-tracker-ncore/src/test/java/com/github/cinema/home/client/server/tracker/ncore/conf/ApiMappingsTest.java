package com.github.cinema.home.client.server.tracker.ncore.conf;

import com.github.cinema.home.client.server.common.types.media.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ApiMappingsTest {
    @Test
    public void givenApiMappings_assertNoGenreUnmapped() {
        for (Genre genre : Genre.values()) {
            Assertions.assertDoesNotThrow(() -> ApiMappings.of(genre));
        }
    }

    @Test
    public void givenApiMappings_assertNoGenreMappedToEmptyString() {
        for (Genre genre : Genre.values()) {
            Assert.assertFalse(StringUtils.isBlank(ApiMappings.of(genre)));
        }
    }
}
