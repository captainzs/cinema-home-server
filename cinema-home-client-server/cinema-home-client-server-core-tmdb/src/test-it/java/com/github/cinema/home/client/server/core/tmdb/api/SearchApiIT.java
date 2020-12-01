package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.core.tmdb.TestContext;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbSearchResult;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class})
public class SearchApiIT {
    @Autowired
    private SearchApi objectUnderTest;
    @SpyBean
    private RestTemplate template;


    //TODO add credits(staff,actors) to tests
    @Test
    public void givenApiKey_whenSearchAMovie_assertAllBasicProperties() {
        TmdbSearchResult<TmdbMovie> result = this.objectUnderTest.requestMovies("The Shawshank Redemption", null, 1);
        Assert.assertEquals(1, result.getPage());
        Assert.assertEquals(1, result.getTotalPages());
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        TmdbMovie movie = result.getResults().get(0);
        Assert.assertFalse(movie.isAdult());
        Assert.assertNull(movie.getBudget());
        Assert.assertNull(movie.getCollection());
        Assert.assertEquals(780278400000L, movie.getReleaseDate().getTime());
        Assert.assertNull(movie.getRevenue());
        Assert.assertNull(movie.getTagline());
        Assert.assertFalse(movie.isVideo());
        Assert.assertEquals("/iNh3BivHyg5sQRPP1KOkzguEX0H.jpg", movie.getBackdropPath());
        Assert.assertNull(movie.getGenres());
        Assert.assertArrayEquals(Arrays.asList(80, 18).toArray(), movie.getGenreIds().toArray());
        Assert.assertNull(movie.getHomepage());
        Assert.assertEquals(278, movie.getId().intValue());
        Assert.assertEquals("en", movie.getOriginalLanguage());
        Assert.assertEquals("The Shawshank Redemption", movie.getOriginalTitle());
        Assert.assertTrue(movie.getOverview().length() > 100);
        Assert.assertTrue(movie.getPopularity() > 10.0);
        Assert.assertEquals("/5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg", movie.getPosterPath());
        Assert.assertNull(movie.getProductionCompanies());
        Assert.assertNull(movie.getProductionCountries());
        Assert.assertNull(movie.getRuntime());
        Assert.assertNull(movie.getStatus());
        Assert.assertEquals("The Shawshank Redemption", movie.getTitle());
        Assert.assertEquals(8.7, movie.getVoteAverage(), 0.1);
        Assert.assertTrue(movie.getVoteCount() > 16900);
        Assert.assertNull(movie.getExternalIds());
        Assert.assertNull(movie.getVideos());
    }

    @Test
    public void givenApiKey_whenSearchAShow_assertAllBasicProperties() {
        TmdbSearchResult<TmdbShow> result = this.objectUnderTest.requestShows("Game of Thrones", 1);
        Assert.assertEquals(1, result.getPage());
        Assert.assertEquals(1, result.getTotalPages());
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        TmdbShow show = result.getResults().get(0);
        Assert.assertNull(show.getCreatedBy());
        Assert.assertEquals(1302998400000L, show.getFirstAirDate().getTime());
        Assert.assertNull(show.isInProduction());
        Assert.assertNull(show.getLanguages());
        Assert.assertNull(show.getLastAirDate());
        Assert.assertNull(show.getLastEpisodeToAir());
        Assert.assertNull(show.getNextEpisodeToAir());
        Assert.assertNull(show.getNetworks());
        Assert.assertNull(show.getNumberOfEpisodes());
        Assert.assertNull(show.getNumberOfSeasons());
        Assert.assertEquals(1, show.getOriginCountry().size());
        Assert.assertEquals("US", show.getOriginCountry().get(0));
        Assert.assertNull(show.getSeasons());
        Assert.assertNull(show.getType());
        Assert.assertTrue(show.getBackdropPath().endsWith(".jpg"));
        Assert.assertNull(show.getGenres());
        Assert.assertEquals(new HashSet<>(Arrays.asList(18, 9648, 10759, 10765)), show.getGenreIds());
        Assert.assertNull(show.getHomepage());
        Assert.assertEquals(1399, show.getId().intValue());
        Assert.assertEquals("en", show.getOriginalLanguage());
        Assert.assertEquals("Game of Thrones", show.getOriginalTitle());
        Assert.assertTrue(show.getOverview().length() > 100);
        Assert.assertTrue(show.getPopularity() > 10.0);
        Assert.assertEquals("/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg", show.getPosterPath());
        Assert.assertNull(show.getProductionCompanies());
        Assert.assertNull(show.getEpisodeRuntimes());
        Assert.assertNull(show.getStatus());
        Assert.assertEquals("Game of Thrones", show.getTitle());
        Assert.assertEquals(8.3, show.getVoteAverage(), 0.1);
        Assert.assertTrue(show.getVoteCount() > 9700);
        Assert.assertNull(show.getExternalIds());
        Assert.assertNull(show.getVideos());
    }
}
