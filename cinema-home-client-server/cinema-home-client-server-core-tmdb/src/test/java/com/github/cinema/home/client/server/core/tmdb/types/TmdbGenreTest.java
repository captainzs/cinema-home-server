package com.github.cinema.home.client.server.core.tmdb.types;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TmdbGenreTest {
    @Test
    public void given2GenresWithSameIdAndName_whenEquals_assertEquals() {
        TmdbGenre g1 = TmdbGenre.of(1, "Name1");
        TmdbGenre g2 = TmdbGenre.of(1, "Name1");
        Assert.assertEquals(g1, g2);
    }

    @Test
    public void given2GenresWithDiffIdAndName_whenEquals_assertNotEquals() {
        TmdbGenre g1 = TmdbGenre.of(1, "Name1");
        TmdbGenre g2 = TmdbGenre.of(2, "Name1");
        Assert.assertNotEquals(g1, g2);
    }

    @Test
    public void given2GenresWithSameIdAndDiffName_whenEquals_assertNotEquals() {
        TmdbGenre g1 = TmdbGenre.of(1, "Name1");
        TmdbGenre g2 = TmdbGenre.of(1, "Name2");
        Assert.assertNotEquals(g1, g2);
    }
}
