package com.github.cinema.home.client.server.common.types.tech;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SourceTest {
    @Test
    public void givenEnumValues_whenClientInterprets_assertOrdinals() {
        Assert.assertEquals(0, Source.CAM_S.ordinal());
        Assert.assertEquals(20, Source.BLURAY_U.ordinal());
    }
}
