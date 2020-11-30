package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.EpisodeId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.domain.Range;

@RunWith(JUnit4.class)
public class SeasonEpisodeParserTest {
    private SeasonEpisodeParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new SeasonEpisodeParser();
    }

    @Test
    public void givenS01E01_With_Spaces() {
        ParseState state = ParseState.of("Tudhattad volna S01E01 1080p");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(1), result.getSeasons());
        Assert.assertEquals(Range.just(1), result.getEpisodes());
        Assert.assertEquals("Tudhattad volna 1080p", state.getRemaining());
    }

    @Test
    public void givenS03E18() {
        ParseState state = ParseState.of("Speechless.S03E18.720p.WEB-DL.H264.Eng.Hun-BNR");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(3), result.getSeasons());
        Assert.assertEquals(Range.just(18), result.getEpisodes());
        Assert.assertEquals("Speechless.720p.WEB-DL.H264.Eng.Hun-BNR", state.getRemaining());
    }

    @Test
    public void givenE01_To_E40() {
        ParseState state = ParseState.of("Irigy Hónaljmirigy E01-E40");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertNull(result.getSeasons());
        Assert.assertEquals(Range.closed(1, 40), result.getEpisodes());
        Assert.assertEquals("Irigy Hónaljmirigy", state.getRemaining());
    }

    @Test
    public void givenS01() {
        ParseState state = ParseState.of("Az elsüllyedt világok S01");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(1), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Az elsüllyedt világok", state.getRemaining());
    }

    @Test
    public void givenS01_S03() {
        ParseState state = ParseState.of("Jackass S01-S03");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.closed(1, 3), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Jackass", state.getRemaining());
    }

    @Test
    public void givenS01_S07() {
        ParseState state = ParseState.of("Murder.She.Wrote.S01-S07.HUN.TvRip.XviD-TrH");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.closed(1, 7), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Murder.She.Wrote.HUN.TvRip.XviD-TrH", state.getRemaining());
    }

    @Test
    public void givenS04() {
        ParseState state = ParseState.of("The.Royals.2015.S04.HUN.BDRip.x264-HNZ");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(4), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("The.Royals.2015.HUN.BDRip.x264-HNZ", state.getRemaining());
    }

    @Test
    public void givenTheCompleteSeriesWithoutSeasonNo() {
        ParseState state = ParseState.of("The.A-Team.The.Complete.Series.DVDRip.XviD.Hun-HDTV");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertNull(result);
        Assert.assertEquals("The.A-Team.The.Complete.Series.DVDRip.XviD.Hun-HDTV", state.getRemaining());
    }

    @Test
    public void givenTheCompleteS07_S07() {
        ParseState state = ParseState.of("Gilmore.Girls.The.Complete.S01-S07.DVDRip.Dual.XviD-vermi");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.closed(1, 7), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Gilmore.Girls.DVDRip.Dual.XviD-vermi", state.getRemaining());
    }

    @Test
    public void givenCompleteWithoutSeasonNo() {
        ParseState state = ParseState.of("A.szerelem.ize.Complete.Read.NFO.720p.WEB-DL.H264.AAC2.0.Hun-TheMilkyWay");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertNull(result);
        Assert.assertEquals("A.szerelem.ize.Complete.Read.NFO.720p.WEB-DL.H264.AAC2.0.Hun-TheMilkyWay", state.getRemaining());
    }

    @Test
    public void givenTheComplete6th() {
        ParseState state = ParseState.of("Suits - S06 - The Complete 6th Season [ 4 BD ] 2016-2017 Blu-ray AVC DTS-HD MA5.1 - d69a74");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(6), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Suits [ 4 BD ] 2016-2017 Blu-ray AVC DTS-HD MA5.1 - d69a74", state.getRemaining());
    }

    @Test
    public void givenComplete3rdSeason() {
        ParseState state = ParseState.of("Spartacus.2013.1080p.CUSTOM.Complete.3rd.Season.EXTENDED.4xBD25.AVC.DTS-HD.MA.x264-HuN-Jak68");
        EpisodeId result = this.objectUnderTest.parseOut(state).getEpisodeId();
        Assert.assertEquals(Range.just(3), result.getSeasons());
        Assert.assertNull(result.getEpisodes());
        Assert.assertEquals("Spartacus.2013.1080p.CUSTOM.EXTENDED.4xBD25.AVC.DTS-HD.MA.x264-HuN-Jak68", state.getRemaining());
    }

    //TODO
    //Dragon.Ball.Z.Season.8.1080p.BluRay.2.0.x264.HUN.ENG.JAP-viktor621
    //Dragon.Ball.Z.1-291.DVDRip.XviD.HUN-Baggio1
}
