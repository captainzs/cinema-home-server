package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.Resolution;
import com.github.cinema.home.client.server.common.types.tech.Scanning;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ResolutionParserTest {
    private ResolutionParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new ResolutionParser();
    }

    @Test
    public void given1080p() {
        ParseState state = ParseState.of("Vivarium.2019.1080p.BluRay.DTS.x264.HuN-No1");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x1080, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("Vivarium.2019.BluRay.DTS.x264.HuN-No1", state.getRemaining());
    }

    @Test
    public void given720p() {
        ParseState state = ParseState.of("Bad.Education.2019.720p.BluRay.x264-SOIGNEUR");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x720, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("Bad.Education.2019.BluRay.x264-SOIGNEUR", state.getRemaining());
    }

    @Test
    public void given1080i() {
        ParseState state = ParseState.of("Simlis.Taxisok.1983.HDTV.1080i.MPEG-2.Hun-Pirosasz");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x1080, result.getResolution());
        Assert.assertEquals(Scanning.INTERLACED, result.getScanning());
        Assert.assertEquals("Simlis.Taxisok.1983.HDTV.MPEG-2.Hun-Pirosasz", state.getRemaining());
    }

    @Test
    public void givenNoPixelsAndCustom4K() {
        ParseState state = ParseState.of("Men.In.Black.1997.Mastered.In.4K.CUSTOM.BD50.AVC.TrueHD.7.1.Atmos.HUN-TheDK");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getResolution());
        Assert.assertNull(result.getScanning());
        Assert.assertEquals("Men.In.Black.1997.Mastered.In.4K.CUSTOM.BD50.AVC.TrueHD.7.1.Atmos.HUN-TheDK", state.getRemaining());
    }

    @Test
    public void given1080psAndConfusing4K() {
        ParseState state = ParseState.of("The.Pink.Panther.Strikes.Again.1976.1080p.4K.Remastered.BluRay.AVC.ReMuX.DTS-HD-2xHuN-Jak68");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x1080, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("The.Pink.Panther.Strikes.Again.1976.4K.Remastered.BluRay.AVC.ReMuX.DTS-HD-2xHuN-Jak68", state.getRemaining());
        state = ParseState.of("The.Graduate.1967.Criterion.Collection.4K.Remastered.1080p.BluRay.Remux.AVC.DTS-HD.MA.5.1.HUN-Essence");
        result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x1080, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("The.Graduate.1967.Criterion.Collection.4K.Remastered.BluRay.Remux.AVC.DTS-HD.MA.5.1.HUN-Essence", state.getRemaining());
    }

    @Test
    public void givenDouble1080p() {
        ParseState state = ParseState.of("Overlord.2018.1080p.1080p.UHD.BluRay.DD+7.1.HDR.x265.HuN-No1");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x1080, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("Overlord.2018.1080p.UHD.BluRay.DD+7.1.HDR.x265.HuN-No1", state.getRemaining());
    }

    @Test
    public void given576p() {
        ParseState state = ParseState.of("Sivatagi.Kommando.1967.HUN.576p.WEB-DL.x264-TOXI");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Resolution.x576, result.getResolution());
        Assert.assertEquals(Scanning.PROGRESSIVE, result.getScanning());
        Assert.assertEquals("Sivatagi.Kommando.1967.HUN.WEB-DL.x264-TOXI", state.getRemaining());
    }
}
