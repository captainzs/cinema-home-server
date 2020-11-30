package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.Source;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(JUnit4.class)
public class SourceParserTest {
    private SourceParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new SourceParser();
    }

    @Test
    public void givenNone() {
        ParseState state = ParseState.of("Bulbbul.2020.720p.x264.E-AC3.HunSub-vTk");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getSources());
        Assert.assertEquals("Bulbbul.2020.720p.x264.E-AC3.HunSub-vTk", state.getRemaining());
    }

    @Test
    public void givenVhsRip() {
        ParseState state = ParseState.of("A.feher.torzsfonok.1983.CUSTOM.HUN.VHSRiP.DivX-TOXI");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Source.VHS_S), result.getSources());
        Assert.assertEquals("A.feher.torzsfonok.1983.CUSTOM.HUN.DivX-TOXI", state.getRemaining());
    }

    @Test
    public void givenBluRay() {
        ParseState state = ParseState.of("National.Geographic.Atmospheres.Earth.Air.and.Water.2008.720p.BluRay.x264-PUZZLE");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Source.BLURAY_H), result.getSources());
        Assert.assertEquals("National.Geographic.Atmospheres.Earth.Air.and.Water.2008.720p.x264-PUZZLE", state.getRemaining());
    }

    @Test
    public void givenHdTv_And_WrongTs() {
        ParseState state = ParseState.of("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org.ts");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.TV_H), result.getSources());
        Assert.assertEquals("RTE.Jihad.Jane.1080i.h264.MP2.MVGroup.org.ts", state.getRemaining());
    }

    @Test
    public void givenWebRip() {
        ParseState state = ParseState.of("The.Way.I.See.It.2020.WEBRip.XviD.MP3-XVID");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.WEB), result.getSources());
        Assert.assertEquals("The.Way.I.See.It.2020.XviD.MP3-XVID", state.getRemaining());
    }

    @Test
    public void givenBluRay2() {
        ParseState state = ParseState.of("Everybodys.Everything.2019.1080p.BluRay.H264.AAC-RARBG");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.BLURAY_H), result.getSources());
        Assert.assertEquals("Everybodys.Everything.2019.1080p.H264.AAC-RARBG", state.getRemaining());
    }

    @Test
    public void givenWeb_Dl() {
        ParseState state = ParseState.of("The.Place.of.No.Words.2019.720p.WEB-DL.XviD.AC3-FGT");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.WEBDL), result.getSources());
        Assert.assertEquals("The.Place.of.No.Words.2019.720p.XviD.AC3-FGT", state.getRemaining());
    }

    @Test
    public void givenRemux() {
        ParseState state = ParseState.of("Soul.Men.2008.1080p.Remux.VC-1.AC-3.5.1.HUN-CRW");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.BLURAY_H), result.getSources());
        Assert.assertEquals("Soul.Men.2008.1080p.VC-1.AC-3.5.1.HUN-CRW", state.getRemaining());
    }

    @Test
    public void givenWeb() {
        ParseState state = ParseState.of("Stargirl.Anders.ist.voellig.normal.2020.German.DL.EAC3.HDR.2160p.WEB.H265.iNTERNAL-muhUHD");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.WEB), result.getSources());
        Assert.assertEquals("Stargirl.Anders.ist.voellig.normal.2020.German.DL.EAC3.HDR.2160p.H265.iNTERNAL-muhUHD", state.getRemaining());
    }

    @Test
    public void givenWebRip2() {
        ParseState state = ParseState.of("[Anime Land] Violet Evergarden - 03 (WEBRip 720p YUV444P10 EAC-3) RAW [7FDF6839].mkv");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.WEB), result.getSources());
        Assert.assertEquals("[Anime Land] Violet Evergarden - 03  720p YUV444P10 EAC-3) RAW [7FDF6839].mkv", state.getRemaining());
    }

    @Test
    public void givenBd50() {
        ParseState state = ParseState.of("Ford.v.Ferrari.2019.UHD.BD50.2160p.HDR10.x265.Atmos.7.1.HUN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.BLURAY_U), result.getSources());
        Assert.assertEquals("Ford.v.Ferrari.2019.UHD.2160p.HDR10.x265.Atmos.7.1.HUN-TRiNiTY", state.getRemaining());
    }


    @Test
    public void givenBdRemux() {
        ParseState state = ParseState.of("Pixels.2015.3D.BDremux.MVC.Atmos.TrueHD.7.1.HUN-USA");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.BLURAY_U), result.getSources());
        Assert.assertEquals("Pixels.2015.3D.MVC.Atmos.TrueHD.7.1.HUN-USA", state.getRemaining());
    }

    @Test
    public void givenBd_Remux() {
        ParseState state = ParseState.of("Dexter.S01.COMPLETE.2006.BD-Remux.TrueHD.HUN-USA");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Source.BLURAY_U), result.getSources());
        Assert.assertEquals("Dexter.S01.COMPLETE.2006.TrueHD.HUN-USA", state.getRemaining());
    }

    private Set<Source> asSet(Source... sources) {
        return new HashSet<>(Arrays.asList(sources));
    }
}
