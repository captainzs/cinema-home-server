package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DynamicRangeParserTest {
    private DynamicRangeParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new DynamicRangeParser();
    }

    @Test
    public void givenNoHdr() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Stavisky.1974.Custom.PAL.DVD9.HUN-Eastwood72"));
        Assert.assertNull(result.isHdr());
    }

    @Test
    public void givenHdr10p_And_DolbyVision() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("The.Invisible.Man.2020.2160p.UHD.BluRay.REMUX.HDR10+.Dolby.Vision.DL.DTS-HD.MA.5.1.HEVC-HUN-UFO971"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenHdr10pDolbyVision() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Saving.Private.Ryan.1998.UHD.BD50.2160p.HDR10+DolbyVision.x265.Atmos.HUN-aNyaM"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenHdr() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Over.the.Moon.2020.1080p.NF.WEB-DL.DDP5.1.Atmos.HEVC.HDR.HUN.ENG-PTHD"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenHdr10() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Ford.v.Ferrari.2019.UHD.BD50.2160p.HDR10.x265.Atmos.7.1.HUN-TRiNiTY"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenHdr10plus() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("1917.2019.REPACK.2160p.UHD.BluRay.HDR10Plus.REMUX.TrueHD.Atmos.7.1.HEVC.HUN-Legacy"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenDolbyVisionDl() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Despicable.Me.3.2017.2160p.BluRay.REMUX.Dolby.Vision.DL.DTS-X.7.1.HEVC-HUN-UFO971"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenDolbyVisionSl() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Back.to.the.Future.Part.III.1990.UHD.BluRay.2160p.REMUX.Dolby.Vision.SL.TrueHD.Atmos.7.1.HEVC.HUN-TRiNiTY"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenDolbyVision_And_Hdr10() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("The.Matrix.1999.x265.Dolby.Vision.HDR10.BD50.Atmos.7.1.Hun-UHDBalek"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenDnr() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Star.Wars.4K77.2160p.UHD.DNR.35mm.x265-v1.0.Dual.HUN-ClunkyChip"));
        Assert.assertTrue(result.isHdr());
    }

    @Test
    public void givenWrongDnrAsGroup() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("The.Drawn.Together.Movie.The.Movie.2010.720p.WEB-DL.AAC2.0.H.264-DNR"));
        Assert.assertNull(result.isHdr());
    }
}
