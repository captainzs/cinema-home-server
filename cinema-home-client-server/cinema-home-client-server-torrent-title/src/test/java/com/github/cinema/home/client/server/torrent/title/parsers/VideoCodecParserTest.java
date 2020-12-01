package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.VideoCodec;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VideoCodecParserTest {
    private VideoCodecParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new VideoCodecParser();
    }

    @Test
    public void givenDivx() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("A.feher.torzsfonok.1983.CUSTOM.HUN.VHSRiP.DivX-TOXI"));
        Assert.assertEquals(VideoCodec.DIVX, result.getVideoCodec());
    }

    @Test
    public void givenX264AndWrongDivx() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Blood.Quantum.2019.1080p.BluRay.x264-RCDiVX"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenX264() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Vivarium.2019.1080p.BluRay.DTS.x264.HuN-No1"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenX_264() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Clouds.2020.1080p.DSNP.WEBRip.DD5.1.X.264-EVO"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenH264() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Rebecca.2020.720p.WEB-DL.DD+5.1.H264.HuN-No1"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenH_264() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Target.Number.One.2020.1080p.WEB-DL.DDP5.1.H.264-CMRG"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenAvc() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Magnolia.1999.1080p.BluRay.REMUX.AVC.DTS-HD.MA.5.1.HuN-TRiNiTY"));
        Assert.assertEquals(VideoCodec.X264, result.getVideoCodec());
    }

    @Test
    public void givenH265() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Love.and.Monsters.2020.HDR.2160p.WEB.H265-NAISU"));
        Assert.assertEquals(VideoCodec.X265, result.getVideoCodec());
    }

    @Test
    public void givenH_265() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Harriet.2019.2160p.WEB-DL.DD+5.1.HDR.H.265-ROCCaT"));
        Assert.assertEquals(VideoCodec.X265, result.getVideoCodec());
    }

    @Test
    public void givenX265() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Whiplash.2014.2160p.UHD.BluRay.TrueHD.7.1.HDR.x265.HuN-TRiNiTY"));
        Assert.assertEquals(VideoCodec.X265, result.getVideoCodec());
    }

    @Test
    public void givenHevc() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Miss.Peregrines.Home.for.Peculiar.Children.2016.2160p.UHD.BluRay.REMUX.TrueHD.Atmos.7.1.HEVC-HUN-UFO971"));
        Assert.assertEquals(VideoCodec.X265, result.getVideoCodec());
    }

    @Test
    public void givenVC1() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Brokeback.Mountain.2005.1080p.BluRay.REMUX.VC1.DTS-HD.MA.5.1.HuN-TRiNiTY"));
        Assert.assertEquals(VideoCodec.VC1, result.getVideoCodec());
    }

    @Test
    public void givenVC_1() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Soul.Men.2008.1080p.Remux.VC-1.AC-3.5.1.HUN-CRW"));
        Assert.assertEquals(VideoCodec.VC1, result.getVideoCodec());
    }

    @Test
    public void givenVP9() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Escape.The.Night.S04.720p.RED.WEBRip.AAC5.1.VP9-ARGN[rartv]"));
        Assert.assertEquals(VideoCodec.VP9, result.getVideoCodec());
    }
}
