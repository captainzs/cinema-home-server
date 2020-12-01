package com.github.cinema.home.client.server.core.fanart;

import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.common.types.media.TvdbId;
import com.github.cinema.home.client.server.core.fanart.types.FanartMovie;
import com.github.cinema.home.client.server.core.fanart.types.FanartShow;
import com.github.cinema.home.client.server.core.fanart.types.FanartDiscImage;
import com.github.cinema.home.client.server.core.fanart.types.FanartImage;
import com.github.cinema.home.client.server.core.fanart.types.FanartSeasonImage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestContext.class})
public class FanartApiServiceIT {
    @Autowired
    private FanartApiService objectUnderTest;
    @SpyBean
    private RestTemplate template;

    @Test
    public void givenImdbId_whenSearchForThorImages_assertPropertiesDeserializedRight() {
        FanartMovie result = this.objectUnderTest.requestMovieArts(ImdbId.of("tt0800369"));
        this.test_Thor(result);
    }

    @Test
    public void givenTmdbId_whenSearchForThorImages_assertPropertiesDeserializedRight() {
        FanartMovie result = this.objectUnderTest.requestMovieArts(TmdbId.of(10195));
        this.test_Thor(result);
    }

    private void test_Thor(FanartMovie result) {
        Assert.assertEquals("Thor", result.getName());
        Assert.assertEquals(10195, result.getTmdbId().intValue());
        Assert.assertEquals("tt0800369", result.getImdbId().strValue());
        Assert.assertEquals(13, result.getHdClearArts().size());
        FanartImage image = result.getHdClearArts().get(0);
        Assert.assertEquals("37494", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/hdmovieclearart/thor-51ae442bcb7b2.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("17", image.getLikes());
        Assert.assertEquals(8, result.getHdClearLogos().size());
        image = result.getHdClearLogos().get(0);
        Assert.assertEquals("27276", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/hdmovielogo/thor-513e338c7ccd9.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("17", image.getLikes());
        Assert.assertEquals(32, result.getPosters().size());
        image = result.getPosters().get(0);
        Assert.assertEquals("65942", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/movieposter/thor-5317faca9b6b9.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("16", image.getLikes());
        Assert.assertEquals(21, result.getBackgrounds().size());
        image = result.getBackgrounds().get(0);
        Assert.assertEquals("5164", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/moviebackground/thor-4fdb71cc85070.jpg", image.getUrl());
        Assert.assertEquals("", image.getIso639Id());
        Assert.assertEquals("11", image.getLikes());
        Assert.assertEquals(12, result.getDiscs().size());
        FanartDiscImage discImage = result.getDiscs().get(0);
        Assert.assertEquals("38519", discImage.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/moviedisc/thor-51b7c7d8a9927.png", discImage.getUrl());
        Assert.assertEquals("en", discImage.getIso639Id());
        Assert.assertEquals("10", discImage.getLikes());
        Assert.assertEquals("1", discImage.getDisc());
        Assert.assertEquals("bluray", discImage.getDiscType());
        Assert.assertEquals(8, result.getThumbs().size());
        image = result.getThumbs().get(0);
        Assert.assertEquals("10650", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/moviethumb/thor-503bf6360e212.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("7", image.getLikes());
        Assert.assertEquals(1, result.getClearArts().size());
        image = result.getClearArts().get(0);
        Assert.assertEquals("5097", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/movieart/thor-4fd900bfe5e90.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("6", image.getLikes());
        Assert.assertEquals(5, result.getBanners().size());
        image = result.getBanners().get(0);
        Assert.assertEquals("107056", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/moviebanner/thor-5544fd83ad2f1.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("5", image.getLikes());
        Assert.assertEquals(2, result.getClearLogos().size());
        image = result.getClearLogos().get(0);
        Assert.assertEquals("369", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/movies/10195/movielogo/thor-4f0a08aa8c9a3.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("5", image.getLikes());
    }

    @Test
    public void givenTvdbId_whenSearchForShowImages_assertPropertiesDeserializedRight() {
        FanartShow result = this.objectUnderTest.requestShowArts(TvdbId.of(360115L));
        Assert.assertEquals("Prison Break", result.getName());
        Assert.assertEquals(360115L, result.getTvdbId().longValue());
        Assert.assertEquals(6, result.getHdClearLogos().size());
        FanartImage image = result.getHdClearLogos().get(0);
        Assert.assertEquals("21426", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/hdtvlogo/prison-break-5067e88ddec40.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("10", image.getLikes());
        Assert.assertEquals(13, result.getPosters().size());
        image = result.getPosters().get(0);
        Assert.assertEquals("35010", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/tvposter/prison-break-5270201661eab.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("9", image.getLikes());
        Assert.assertEquals(3, result.getHdClearArts().size());
        image = result.getHdClearArts().get(0);
        Assert.assertEquals("24616", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/hdclearart/prison-break-50f858ba362da.png", image.getUrl());
        Assert.assertEquals("de", image.getIso639Id());
        Assert.assertEquals("7", image.getLikes());
        Assert.assertEquals(12, result.getSeasonPosters().size());
        FanartSeasonImage seasonImg = result.getSeasonPosters().get(0);
        Assert.assertEquals("39273", seasonImg.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/seasonposter/prison-break-5352d88491590.jpg", seasonImg.getUrl());
        Assert.assertEquals("en", seasonImg.getIso639Id());
        Assert.assertEquals("7", seasonImg.getLikes());
        Assert.assertEquals("1", seasonImg.getSeason());
        Assert.assertEquals(11, result.getBackgrounds().size());
        image = result.getBackgrounds().get(0);
        Assert.assertEquals("74083", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/showbackground/prison-break-58ec95f57e799.jpg", image.getUrl());
        Assert.assertEquals("", image.getIso639Id());
        Assert.assertEquals("5", image.getLikes());
        Assert.assertEquals(2, result.getCharacterArts().size());
        image = result.getCharacterArts().get(0);
        Assert.assertEquals("18079", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/characterart/prison-break-4fb6d22701790.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("5", image.getLikes());
        Assert.assertEquals(2, result.getBanners().size());
        image = result.getBanners().get(0);
        Assert.assertEquals("51256", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/tvbanner/prison-break-552d3ec39a1a0.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("4", image.getLikes());
        Assert.assertEquals(5, result.getClearArts().size());
        image = result.getClearArts().get(0);
        Assert.assertEquals("24626", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/clearart/prison-break-50f872b5ccac0.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("3", image.getLikes());
        Assert.assertEquals(2, result.getClearLogos().size());
        image = result.getClearLogos().get(0);
        Assert.assertEquals("10081", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/clearlogo/prison-break-4db155fc44877.png", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("3", image.getLikes());
        Assert.assertEquals(6, result.getSeasonThumbs().size());
        seasonImg = result.getSeasonThumbs().get(0);
        Assert.assertEquals("6601", seasonImg.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/seasonthumb/Prison Break (2).jpg", seasonImg.getUrl());
        Assert.assertEquals("en", seasonImg.getIso639Id());
        Assert.assertEquals("3", seasonImg.getLikes());
        Assert.assertEquals("1", seasonImg.getSeason());
        Assert.assertEquals(7, result.getThumbs().size());
        image = result.getThumbs().get(0);
        Assert.assertEquals("74703", image.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/tvthumb/prison-break-59059e40bebb1.jpg", image.getUrl());
        Assert.assertEquals("en", image.getIso639Id());
        Assert.assertEquals("3", image.getLikes());
        Assert.assertEquals(6, result.getSeasonBanners().size());
        seasonImg = result.getSeasonBanners().get(0);
        Assert.assertEquals("70563", seasonImg.getId());
        Assert.assertEquals("https://assets.fanart.tv/fanart/tv/360115/seasonbanner/prison-break-587b1e8110b95.jpg", seasonImg.getUrl());
        Assert.assertEquals("en", seasonImg.getIso639Id());
        Assert.assertEquals("1", seasonImg.getLikes());
        Assert.assertEquals("1", seasonImg.getSeason());
    }
}
