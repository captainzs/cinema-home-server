package com.github.cinema.home.client.server.tracker.ncore;

import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.SortBy;
import com.github.cinema.home.client.server.common.types.SortOrder;
import com.github.cinema.home.client.server.common.types.media.Genre;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(JUnit4.class)
public class UrlMakerTest {
    private UrlMaker objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new UrlMaker();
    }

    @Test
    public void assertLoginUrlIsNotMalformed_thusExceptionNotThrown() {
        Assertions.assertDoesNotThrow(this.objectUnderTest::logInUrl);
    }

    @Test
    public void assertRecommendedUrlIsNotMalformed_thusExceptionNotThrown() {
        Assertions.assertDoesNotThrow(this.objectUnderTest::recommendedUrl);
    }

    @Test
    public void givenSearchFilter_whenGetSearchUrlForMovies_assertUrl() {
        SearchFilter filter = Mockito.mock(SearchFilter.class);
        Mockito.when(filter.getSubText()).thenReturn("MyMovie");
        Mockito.when(filter.getSortBy()).thenReturn(SortBy.POPULARITY);
        Mockito.when(filter.getSortOrder()).thenReturn(SortOrder.ASCENDING);
        Mockito.when(filter.getGenres()).thenReturn(new HashSet<>());

        String url = this.objectUnderTest.searchUrl(filter, 0, MediaType.MOVIE).toString();
        MatcherAssert.assertThat(url, Matchers.containsString("oldal=1"));
        MatcherAssert.assertThat(url, Matchers.containsString("miszerint=seeders"));
        MatcherAssert.assertThat(url, Matchers.containsString("hogyan=ASC"));
        MatcherAssert.assertThat(url, Matchers.containsString("=dvd9_hun,xvid,dvd,dvd9,hd_hun,xvid_hun,dvd_hun,hd&"));
        MatcherAssert.assertThat(url, Matchers.containsString("tags=&"));
        MatcherAssert.assertThat(url, Matchers.containsString("mire=MyMovie&"));
        MatcherAssert.assertThat(url, Matchers.containsString("miben=name"));
        MatcherAssert.assertThat(url, Matchers.not(Matchers.containsString("null")));
    }

    @Test
    public void givenSearchFilter_whenGetSearchUrlForShows_assertUrl() {
        SearchFilter filter = Mockito.mock(SearchFilter.class);
        Mockito.when(filter.getSubText()).thenReturn("");
        Mockito.when(filter.getSortBy()).thenReturn(SortBy.POPULARITY);
        Mockito.when(filter.getSortOrder()).thenReturn(SortOrder.ASCENDING);
        Mockito.when(filter.getGenres()).thenReturn(new HashSet<>(Arrays.asList(Genre.DRAMA, Genre.ACTION)));

        String url = this.objectUnderTest.searchUrl(filter, 0, MediaType.SHOW).toString();
        MatcherAssert.assertThat(url, Matchers.containsString("oldal=1"));
        MatcherAssert.assertThat(url, Matchers.containsString("miszerint=seeders"));
        MatcherAssert.assertThat(url, Matchers.containsString("hogyan=ASC"));
        MatcherAssert.assertThat(url, Matchers.containsString("=xvidser_hun,xvidser,dvdser_hun,dvd9ser_hun,dvdser,hdser,hdser_hun,dvd9ser&"));
        MatcherAssert.assertThat(url, Matchers.containsString("akcio"));
        MatcherAssert.assertThat(url, Matchers.containsString("drama"));
        MatcherAssert.assertThat(url, Matchers.containsString("miben=name"));
        MatcherAssert.assertThat(url, Matchers.not(Matchers.containsString("null")));
    }

    @Test
    public void givenImdbId_whenGetSearchUrlForMovies_assertUrl() {
        String url = this.objectUnderTest.searchUrl(ImdbId.of("tt1111"), 2, MediaType.MOVIE).toString();
        MatcherAssert.assertThat(url, Matchers.containsString("oldal=2"));
        MatcherAssert.assertThat(url, Matchers.containsString("miszerint=seeders"));
        MatcherAssert.assertThat(url, Matchers.containsString("hogyan=DESC"));
        MatcherAssert.assertThat(url, Matchers.containsString("=dvd9_hun,xvid,dvd,dvd9,hd_hun,xvid_hun,dvd_hun,hd&"));
        MatcherAssert.assertThat(url, Matchers.containsString("mire=tt1111&"));
        MatcherAssert.assertThat(url, Matchers.containsString("miben=imdb"));
        MatcherAssert.assertThat(url, Matchers.not(Matchers.containsString("null")));
    }

    @Test
    public void givenImdbId_whenGetSearchUrlForShows_assertUrl() {
        String url = this.objectUnderTest.searchUrl(ImdbId.of("tt1111"), 22, MediaType.SHOW).toString();
        MatcherAssert.assertThat(url, Matchers.containsString("oldal=22"));
        MatcherAssert.assertThat(url, Matchers.containsString("miszerint=seeders"));
        MatcherAssert.assertThat(url, Matchers.containsString("hogyan=DESC"));
        MatcherAssert.assertThat(url, Matchers.containsString("=xvidser_hun,xvidser,dvdser_hun,dvd9ser_hun,dvdser,hdser,hdser_hun,dvd9ser&"));
        MatcherAssert.assertThat(url, Matchers.containsString("mire=tt1111&"));
        MatcherAssert.assertThat(url, Matchers.containsString("miben=imdb"));
        MatcherAssert.assertThat(url, Matchers.not(Matchers.containsString("null")));
    }

    @Test
    public void givenNcoreId_whenGetDetailsUrl_assertUrl() {
        String url = this.objectUnderTest.detailsUrl("32589").toString();
        MatcherAssert.assertThat(url, Matchers.containsString("action=details"));
        MatcherAssert.assertThat(url, Matchers.containsString("id=32589"));
        MatcherAssert.assertThat(url, Matchers.not(Matchers.containsString("null")));
    }
}
