package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NetworkApiMappingsTest {
    @Test
    public void givenApiMappings_assertNoNetworkUnmapped() {
        for (NetworkGroup network : NetworkGroup.values()) {
            Assertions.assertDoesNotThrow(() -> NetworkApiMappings.of(network));
        }
    }

    @Test
    public void givenApiMappings_assertNoNetworkMappedToNull() {
        for (NetworkGroup network : NetworkGroup.values()) {
            Assert.assertNotNull(NetworkApiMappings.of(network));
        }
    }

    @Test
    public void givenNullNetwork_whenMap_assertEmptySetReturned() {
        Assert.assertTrue(NetworkApiMappings.of(null).isEmpty());
    }

    @Test
    public void givenNullTmdbNetworks_whenToNetworks_assertEmptySetReturned() {
        Assert.assertTrue(NetworkApiMappings.toNetworks(null).isEmpty());
    }
}
