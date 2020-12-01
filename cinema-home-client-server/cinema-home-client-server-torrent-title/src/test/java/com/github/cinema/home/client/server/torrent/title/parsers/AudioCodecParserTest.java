package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.AudioCodec;
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
public class AudioCodecParserTest {
    private AudioCodecParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new AudioCodecParser();
    }

    @Test
    public void givenNone() {
        ParseState state = ParseState.of("A.feher.torzsfonok.1983.CUSTOM.HUN.VHSRiP.DivX-TOXI");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("A.feher.torzsfonok.1983.CUSTOM.HUN.VHSRiP.DivX-TOXI", state.getRemaining());
    }

    @Test
    public void givenNone2() {
        ParseState state = ParseState.of("National.Geographic.Atmospheres.Earth.Air.and.Water.2008.720p.BluRay.x264-PUZZLE");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("National.Geographic.Atmospheres.Earth.Air.and.Water.2008.720p.BluRay.x264-PUZZLE", state.getRemaining());
    }

    @Test
    public void givenMp2() {
        ParseState state = ParseState.of("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org.ts");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.MP2), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("RTE.Jihad.Jane.1080i.HDTV.h264.MVGroup.org.ts", state.getRemaining());
    }

    @Test
    public void givenMp3() {
        ParseState state = ParseState.of("The.Way.I.See.It.2020.WEBRip.XviD.MP3-XVID");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.MP3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("The.Way.I.See.It.2020.WEBRip.XviD-XVID", state.getRemaining());
    }

    @Test
    public void givenAac() {
        ParseState state = ParseState.of("Everybodys.Everything.2019.1080p.BluRay.H264.AAC-RARBG");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.AAC), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Everybodys.Everything.2019.1080p.BluRay.H264-RARBG", state.getRemaining());
    }

    @Test
    public void givenAc3() {
        ParseState state = ParseState.of("The.Place.of.No.Words.2019.720p.WEB-DL.XviD.AC3-FGT");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.AC3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("The.Place.of.No.Words.2019.720p.WEB-DL.XviD-FGT", state.getRemaining());
    }

    @Test
    public void givenAc_3() {
        ParseState state = ParseState.of("Soul.Men.2008.1080p.Remux.VC-1.AC-3.5.1.HUN-CRW");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.AC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Soul.Men.2008.1080p.Remux.VC-1.HUN-CRW", state.getRemaining());
    }

    @Test
    public void givenDd() {
        ParseState state = ParseState.of("Bad.Education.2019.720p.BluRay.DD.5.1.x264.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.AC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Bad.Education.2019.720p.BluRay.x264.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDd_With_AdditionSign() {
        ParseState state = ParseState.of("Spartacus.1960.1080p.UHD.BluRay.DD+7.1.x264.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Spartacus.1960.1080p.UHD.BluRay.x264.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDdp() {
        ParseState state = ParseState.of("Long.Way.Up.S01E08.Ecuador.1080p.ATVP.WEB-DL.DDP5.1.H.264-NTb");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Long.Way.Up.S01E08.Ecuador.1080p.ATVP.WEB-DL.H.264-NTb", state.getRemaining());
    }

    @Test
    public void givenDdPlus() {
        ParseState state = ParseState.of("Stay.Alive.[2006].Web-DL.x264.1080p.AVC.DDPlus5.1.Hun -d69a74");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Stay.Alive.[2006].Web-DL.x264.1080p.AVC.Hun -d69a74", state.getRemaining());
    }

    @Test
    public void givenEac3() {
        ParseState state = ParseState.of("Stargirl.Anders.ist.voellig.normal.2020.German.DL.EAC3.HDR.2160p.WEB.H265.iNTERNAL-muhUHD");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Stargirl.Anders.ist.voellig.normal.2020.German.DL.HDR.2160p.WEB.H265.iNTERNAL-muhUHD", state.getRemaining());
    }

    @Test
    public void givenE_Ac3() {
        ParseState state = ParseState.of("Bulbbul.2020.720p.x264.E-AC3.HunSub-vTk");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Bulbbul.2020.720p.x264.HunSub-vTk", state.getRemaining());
    }

    @Test
    public void givenEac_3() {
        ParseState state = ParseState.of("[Anime Land] Violet Evergarden - 03 (WEBRip 720p YUV444P10 EAC-3) RAW [7FDF6839].mkv");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("[Anime Land] Violet Evergarden - 03 (WEBRip 720p YUV444P10) RAW [7FDF6839].mkv", state.getRemaining());
    }

    @Test
    public void givenE_Ac_3() {
        ParseState state = ParseState.of("[Harunatsu] Beatless - 03 [720p Hi444PP E-AC-3][E869BFCB].mkv");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("[Harunatsu] Beatless - 03 [720p Hi444PP][E869BFCB].mkv", state.getRemaining());
    }

    @Test
    public void givenDts() {
        ParseState state = ParseState.of("Edwin.Boyd.2011.720p.BluRay.DTS.x264-SbR");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.DTS), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Edwin.Boyd.2011.720p.BluRay.x264-SbR", state.getRemaining());
    }

    @Test
    public void givenDtsEs() {
        ParseState state = ParseState.of("Star Wars.Episode.1.The.Phantom.Menace.1999.BluRay.1080p.DTSES.x264-CHD");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.DTS_ES), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Star Wars.Episode.1.The.Phantom.Menace.1999.BluRay.1080p.x264-CHD", state.getRemaining());
    }

    @Test
    public void givenDts_Es() {
        ParseState state = ParseState.of("The.Dukes.of.Hazzard.2005.1080p.WEB-DL.DTS-ES.Matrix.x264.HuN-BAYLEE");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.DTS_ES), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("The.Dukes.of.Hazzard.2005.1080p.WEB-DL.x264.HuN-BAYLEE", state.getRemaining());
    }

    @Test
    public void givenFlac() {
        ParseState state = ParseState.of("Motel.Hell.1980.REMASTERED.1080p.BluRay.x264.FLAC.2.0-iFT");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.FLAC), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("2.0"), result.getAudioChannels());
        Assert.assertEquals("Motel.Hell.1980.REMASTERED.1080p.BluRay.x264-iFT", state.getRemaining());
    }

    @Test
    public void givenDolbyTrueHd_And_Dts() {
        ParseState state = ParseState.of("Natural.Born.Killers.D.C.1994.Blu-Ray.1080p.VC-1.Dolby.TrueHD.Hun.DTS.Remux-Triad");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.TRUE_HD, AudioCodec.DTS), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Natural.Born.Killers.D.C.1994.Blu-Ray.1080p.VC-1.Hun.Remux-Triad", state.getRemaining());
    }

    @Test
    public void givenTrueHdAtmos() {
        ParseState state = ParseState.of("21.Jump.Street.2012.2160p.UHD.BluRay.TrueHD.Atmos.7.1.HDR.x265.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("21.Jump.Street.2012.2160p.UHD.BluRay.HDR.x265.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenTrueHd_And_Atmos() {
        ParseState state = ParseState.of("Sing.2016.2160p.BluRay.REMUX.HEVC.TrueHD.7.1.Atmos-HUN-UFO971");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Sing.2016.2160p.BluRay.REMUX.HEVC-HUN-UFO971", state.getRemaining());
    }

    @Test
    public void givenDdp_Atmos() {
        ParseState state = ParseState.of("Over.the.Moon.2020.1080p.NF.WEB-DL.DDP5.1.Atmos.HEVC.HDR.HUN.ENG-PTHD");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.EAC3), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Over.the.Moon.2020.1080p.NF.WEB-DL.HEVC.HDR.HUN.ENG-PTHD", state.getRemaining());
    }

    @Test
    public void givenTrueHd() {
        ParseState state = ParseState.of("Black.Hawk.Down.2001.Extended.2160p.UHD.BluRay.TrueHD.7.1.HDR.x265.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Black.Hawk.Down.2001.Extended.2160p.UHD.BluRay.HDR.x265.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenAtmos() {
        ParseState state = ParseState.of("Ford.v.Ferrari.2019.UHD.BD50.2160p.HDR10.x265.Atmos.7.1.HUN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Ford.v.Ferrari.2019.UHD.BD50.2160p.HDR10.x265.HUN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDpp_And_Atmos() {
        ParseState state = ParseState.of("Over.the.Moon.2020.1080p.NF.WEB-DL.DDP5.1.Atmos.7.1.HEVC.HDR.HUN.ENG-PTHD");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.EAC3, AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(this.asSet("5.1", "7.1"), result.getAudioChannels());
        Assert.assertEquals("Over.the.Moon.2020.1080p.NF.WEB-DL.HEVC.HDR.HUN.ENG-PTHD", state.getRemaining());
    }

    @Test
    public void givenAtmosTrueHd() {
        ParseState state = ParseState.of("Pixels.2015.3D.BDremux.MVC.Atmos.TrueHD.7.1.HUN-USA");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Pixels.2015.3D.BDremux.MVC.HUN-USA", state.getRemaining());
    }

    @Test
    public void givenDtsHdMa_And_DolbyAtmos() {
        ParseState state = ParseState.of("John.Wick.2014.1080p.Bluray.Custom.Remux.AVC.DTS-HD.MA.Dolby.Atmos.HUN-KuNgZi");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD, AudioCodec.TRUE_HD), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("John.Wick.2014.1080p.Bluray.Custom.Remux.AVC.HUN-KuNgZi", state.getRemaining());
    }

    @Test
    public void givenDts_Hr() {
        ParseState state = ParseState.of("The.Devils.Rejects.Unrated.BDRemux.MPEG2.DTS-HR.EnG.HuN-Nb");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("The.Devils.Rejects.Unrated.BDRemux.MPEG2.EnG.HuN-Nb", state.getRemaining());
    }

    @Test
    public void givenDts_HdHra() {
        ParseState state = ParseState.of("Die.Welle.2008.1080p.BluRay.REMUX.AVC.DTS-HD.HRA.5.1.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Die.Welle.2008.1080p.BluRay.REMUX.AVC.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDts_HdHr() {
        ParseState state = ParseState.of("Fast.and.Furious.7.2015.Extended.2160p.UHD.HDR.BluRay.DTS-HD.HR.7.1.x265.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Fast.and.Furious.7.2015.Extended.2160p.UHD.HDR.BluRay.x265.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDts_HdMa() {
        ParseState state = ParseState.of("Supernatural.S12E14.1080p.BluRay.REMUX.AVC.DTS-HD.MA.5.1.HUN.ENG-SFY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("Supernatural.S12E14.1080p.BluRay.REMUX.AVC.HUN.ENG-SFY", state.getRemaining());
    }

    @Test
    public void givenDts_Hd_Ma() {
        ParseState state = ParseState.of("The 100 - S05 - The Complete 5th Season [ 3 BD ] 2018 Blu-ray AVC DTS-HD MA5.1 - d69a74");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("5.1"), result.getAudioChannels());
        Assert.assertEquals("The 100 - S05 - The Complete 5th Season [ 3 BD ] 2018 Blu-ray AVC - d69a74", state.getRemaining());
    }

    @Test
    public void givenDts_Hd() {
        ParseState state = ParseState.of("TRON.LEGACY.2010.MVC.3D.BD25.Re-Encoded.DTS-HD.7.1.Hun-HDPhoenix");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("TRON.LEGACY.2010.MVC.3D.BD25.Re-Encoded.Hun-HDPhoenix", state.getRemaining());
    }

    @Test
    public void givenDtsHd() {
        ParseState state = ParseState.of("Murder.by.Death.1976.BD50.AVC.DTSHD.HUN.2.0-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_HD), result.getAudioCodecs());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Murder.by.Death.1976.BD50.AVC.HUN.2.0-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDts_X() {
        ParseState state = ParseState.of("Despicable.Me.2.2013.2160p.BluRay.REMUX.Dolby.Vision.DL.DTS-X.7.1.HEVC-HUN-UFO971");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_X), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Despicable.Me.2.2013.2160p.BluRay.REMUX.Dolby.Vision.DL.HEVC-HUN-UFO971", state.getRemaining());
    }

    @Test
    public void givenDtsX() {
        ParseState state = ParseState.of("Charlies.Angels.2019.REMUX.2160p.UHD.BluRay.DTSX.7.1.HDR.HEVC.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_X), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Charlies.Angels.2019.REMUX.2160p.UHD.BluRay.HDR.HEVC.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenDtsXMa() {
        ParseState state = ParseState.of("Night.School.2018.2160p.Theatrical.Cut.Bluray.Remux.HEVC.DTSX.MA.7.1.HUN-KuNgZi");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.DTS_X), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("7.1"), result.getAudioChannels());
        Assert.assertEquals("Night.School.2018.2160p.Theatrical.Cut.Bluray.Remux.HEVC.HUN-KuNgZi", state.getRemaining());
    }

    @Test
    public void givenPcm() {
        ParseState state = ParseState.of("Touch.of.Evil.1958.BluRay.REMUX.AVC.PCM.2.0.HuN-TRiNiTY");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.PCM), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("2.0"), result.getAudioChannels());
        Assert.assertEquals("Touch.of.Evil.1958.BluRay.REMUX.AVC.HuN-TRiNiTY", state.getRemaining());
    }

    @Test
    public void givenLpcm() {
        ParseState state = ParseState.of("Johnny.Handsome.1989.1080p.Remux.AVC.LPCM.2.0.HUN-CRW");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(AudioCodec.PCM), result.getAudioCodecs());
        Assert.assertEquals(Collections.singleton("2.0"), result.getAudioChannels());
        Assert.assertEquals("Johnny.Handsome.1989.1080p.Remux.AVC.HUN-CRW", state.getRemaining());
    }

    private Set<AudioCodec> asSet(AudioCodec... codecs) {
        return new HashSet<>(Arrays.asList(codecs));
    }

    private Set<String> asSet(String... channels) {
        return new HashSet<>(Arrays.asList(channels));
    }
}
