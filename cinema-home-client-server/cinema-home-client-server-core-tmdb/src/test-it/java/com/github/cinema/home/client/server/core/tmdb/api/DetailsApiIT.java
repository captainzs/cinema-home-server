package com.github.cinema.home.client.server.core.tmdb.api;

import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.TestContext;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbCompany;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbGenre;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbMovie;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbSeason;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbVideo;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbShow;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class})
public class DetailsApiIT {
    @Autowired
    private DetailsApi objectUnderTest;
    @SpyBean
    private RestTemplate template;

    @Test
    public void givenApiKey_whenSearchADetailedMovie_assertAllProperties() {
        TmdbMovie movie = this.objectUnderTest.requestMovie(TmdbId.of(278), Locale.ENGLISH);
        Assert.assertFalse(movie.isAdult());
        Assert.assertEquals(25000000L, movie.getBudget().longValue());
        Assert.assertNull(movie.getCollection());
        Assert.assertEquals(780278400000L, movie.getReleaseDate().getTime());
        Assert.assertEquals(28341469L, movie.getRevenue().longValue());
        Assert.assertEquals("Fear can hold you prisoner. Hope can set you free.", movie.getTagline());
        Assert.assertFalse(movie.isVideo());
        Assert.assertEquals("/iNh3BivHyg5sQRPP1KOkzguEX0H.jpg", movie.getBackdropPath());
        Assert.assertArrayEquals(Arrays.asList(TmdbGenre.of(18, "Drama"), TmdbGenre.of(80, "Crime")).toArray(),
                movie.getGenres().toArray());
        Assert.assertNull(movie.getGenreIds());
        Assert.assertNull(movie.getHomepage());
        Assert.assertEquals(278, movie.getId().intValue());
        Assert.assertEquals("en", movie.getOriginalLanguage());
        Assert.assertEquals("The Shawshank Redemption", movie.getOriginalTitle());
        Assert.assertTrue(movie.getOverview().length() > 100);
        Assert.assertTrue(movie.getPopularity() > 10.0);
        Assert.assertEquals("/5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg", movie.getPosterPath());
        Assert.assertEquals(1, movie.getProductionCompanies().size());
        Assert.assertEquals(97, movie.getProductionCompanies().get(0).getId());
        Assert.assertEquals(1, movie.getProductionCountries().size());
        Assert.assertEquals("US", movie.getProductionCountries().iterator().next().getIsoId());
        Assert.assertEquals(142, movie.getRuntime().intValue());
        Assert.assertEquals("Released", movie.getStatus());
        Assert.assertEquals("The Shawshank Redemption", movie.getTitle());
        Assert.assertEquals(8.7, movie.getVoteAverage(), 0.1);
        Assert.assertTrue(movie.getVoteCount() > 16900);
        Assert.assertEquals("tt0111161", movie.getExternalIds().getImdbId().strValue());
        Assert.assertNull(movie.getExternalIds().getTvdbId());
        Assert.assertEquals(6, movie.getVideos().getResults().size());
        TmdbVideo video = movie.getVideos().getResults().get(0);
        Assert.assertFalse(video.getId().isEmpty());
        Assert.assertEquals("en", video.getIso639Id());
        Assert.assertEquals("US", video.getIso3166Id());
        Assert.assertTrue(video.getKey().length() > 0);
        Assert.assertTrue(video.getName().length() > 0);
        Assert.assertTrue(video.getSize() > 0);
        Assert.assertEquals("YouTube", video.getSite());
        Assert.assertTrue(video.getType().length() > 0);
    }

    @Test
    public void givenApiKey_whenSearchADetailedShow_assertAllProperties() {
        TmdbShow show = this.objectUnderTest.requestShow(TmdbId.of(1399), Locale.ENGLISH);
        Assert.assertTrue(show.getBackdropPath().endsWith(".jpg"));
        Assert.assertEquals(2, show.getCreatedBy().size());
        Assert.assertEquals(9813, show.getCreatedBy().get(0).getId());
        Assert.assertEquals("5256c8c219c2956ff604858a", show.getCreatedBy().get(0).getCreditId());
        Assert.assertEquals("David Benioff", show.getCreatedBy().get(0).getName());
        Assert.assertEquals(2, show.getCreatedBy().get(0).getGender());
        Assert.assertEquals("/xvNN5huL0X8yJ7h3IZfGG4O2zBD.jpg", show.getCreatedBy().get(0).getProfilePath());
        Assert.assertEquals(228068, show.getCreatedBy().get(1).getId());
        Assert.assertEquals(1302998400000L, show.getFirstAirDate().getTime());
        Assert.assertFalse(show.isInProduction());
        Assert.assertEquals(1, show.getLanguages().size());
        Assert.assertEquals("en", show.getLanguages().get(0));
        Assert.assertEquals(1558224000000L, show.getLastAirDate().getTime());
        Assert.assertEquals(1558224000000L, show.getLastEpisodeToAir().getAirDate().getTime());
        Assert.assertEquals(6, show.getLastEpisodeToAir().getEpisodeNo().intValue());
        Assert.assertEquals(1551830, show.getLastEpisodeToAir().getId().intValue());
        Assert.assertEquals("The Iron Throne", show.getLastEpisodeToAir().getName());
        Assert.assertTrue(show.getLastEpisodeToAir().getOverview().length() > 50);
        Assert.assertEquals("806", show.getLastEpisodeToAir().getProductionCode());
        Assert.assertEquals("/3x8tJon5jXFa1ziAM93hPKNyW7i.jpg", show.getLastEpisodeToAir().getStillPath());
        Assert.assertEquals(4, show.getLastEpisodeToAir().getVoteAverage(), 1.0);
        Assert.assertTrue(show.getLastEpisodeToAir().getVoteCount() > 95);
        Assert.assertNull(show.getNextEpisodeToAir());
        Assert.assertEquals(1, show.getNetworks().size());
        TmdbCompany network = show.getNetworks().iterator().next();
        Assert.assertEquals("HBO", network.getName());
        Assert.assertEquals(49, network.getId());
        Assert.assertEquals("/tuomPhY2UtuPTqqFnKMVHvSb724.png", network.getLogoPath());
        Assert.assertEquals("US", network.getOriginCountry());
        Assert.assertEquals(73, show.getNumberOfEpisodes().intValue());
        Assert.assertEquals(8, show.getNumberOfSeasons().intValue());
        Assert.assertEquals(1, show.getOriginCountry().size());
        Assert.assertEquals("US", show.getOriginCountry().get(0));
        Assert.assertEquals("en", show.getOriginalLanguage());
        Assert.assertEquals("Game of Thrones", show.getOriginalTitle());
        Assert.assertTrue(show.getOverview().length() > 100);
        Assert.assertTrue(show.getPopularity() > 10.0);
        Assert.assertEquals("/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg", show.getPosterPath());
        Assert.assertEquals(4, show.getProductionCompanies().size());
        Assert.assertEquals(9, show.getSeasons().size());
        TmdbSeason season0 = show.getSeasons().get(0);
        Assert.assertEquals(1291507200000L, season0.getAirDate().getTime());
        Assert.assertTrue(season0.getEpisodeCount() > 53);
        Assert.assertEquals(3627, season0.getId().intValue());
        Assert.assertEquals("Specials", season0.getName());
        Assert.assertNull(season0.getOverview());
        Assert.assertEquals("/kMTcwNRfFKCZ0O2OaBZS0nZ2AIe.jpg", season0.getPosterPath());
        Assert.assertEquals(0, season0.getSeasonNo().intValue());
        Assert.assertEquals("Ended", show.getStatus());
        Assert.assertEquals("Scripted", show.getType());
        Assert.assertEquals(new HashSet<>(Arrays.asList(
                TmdbGenre.of(10765, "Sci-Fi & Fantasy"),
                TmdbGenre.of(18, "Drama"),
                TmdbGenre.of(10759, "Action & Adventure"),
                TmdbGenre.of(9648, "Mystery"))),
                show.getGenres());
        Assert.assertNull(show.getGenreIds());
        Assert.assertEquals("http://www.hbo.com/game-of-thrones", show.getHomepage());
        Assert.assertEquals(1399, show.getId().intValue());
        Assert.assertEquals("Game of Thrones", show.getTitle());
        Assert.assertEquals(8.3, show.getVoteAverage(), 0.1);
        Assert.assertTrue(show.getVoteCount() > 9700);
        Assert.assertEquals(60.0, show.getAverageRuntime(), 0.0);
        Assert.assertEquals("tt0944947", show.getExternalIds().getImdbId().strValue());
        Assert.assertEquals(121361L, show.getExternalIds().getTvdbId().longValue());
        Assert.assertEquals(4, show.getVideos().getResults().size());
        TmdbVideo video = show.getVideos().getResults().get(0);
        Assert.assertFalse(video.getId().isEmpty());
        Assert.assertEquals("en", video.getIso639Id());
        Assert.assertEquals("US", video.getIso3166Id());
        Assert.assertFalse(StringUtils.isEmpty(video.getKey()));
        Assert.assertFalse(StringUtils.isEmpty(video.getName()));
        Assert.assertEquals(1080, video.getSize().intValue());
        Assert.assertEquals("YouTube", video.getSite());
        Assert.assertEquals("Trailer", video.getType());
    }
}
