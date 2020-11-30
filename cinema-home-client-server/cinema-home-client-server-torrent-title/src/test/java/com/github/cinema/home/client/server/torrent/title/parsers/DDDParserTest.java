package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DDDParserTest {
    private DDDParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new DDDParser();
    }

    @Test
    public void givenNone() {
        ParseState state = this.progressedState("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org.ts", 15);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.is3d());
        Assert.assertEquals("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org.ts", state.getRemaining());
    }

    @Test
    public void given3D() {
        ParseState state = this.progressedState("TRON.LEGACY.2010.MVC.3D.BD25.Re-Encoded.DTS-HD.7.1.Hun-HDPhoenix", 12);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.is3d());
        Assert.assertEquals("TRON.LEGACY.2010.MVC.BD25.Re-Encoded.DTS-HD.7.1.Hun-HDPhoenix", state.getRemaining());
    }

    @Test
    public void givenAnaglyph() {
        ParseState state = this.progressedState("Adult4D.12.01.04.Melissa.Odara.And.Bererdou.Brazilian.Bunda.ANAGLYPH.XXX.1080p.WMV-KTR", 59);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.is3d());
        Assert.assertEquals("Adult4D.12.01.04.Melissa.Odara.And.Bererdou.Brazilian.Bunda.XXX.1080p.WMV-KTR", state.getRemaining());
    }

    @Test
    public void given3D_And_Half_Ou() {
        ParseState state = this.progressedState("Onward.2020.1080p.3D.Blu-ray.Half-OU.x264.DTS-HD.7.1.Hun-Fodyna", 7);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.is3d());
        Assert.assertEquals("Onward.2020.1080p.Blu-ray.x264.DTS-HD.7.1.Hun-Fodyna", state.getRemaining());
    }

    @Test
    public void given3D_And_Half_Sbs() {
        ParseState state = this.progressedState("Aladdin.2019.READ.NFO.1080p.3D.BluRay.Half-SBS.DD+7.1.x264.HUN-WB", 8);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.is3d());
        Assert.assertEquals("Aladdin.2019.READ.NFO.1080p.BluRay.DD+7.1.x264.HUN-WB", state.getRemaining());
    }

    private ParseState progressedState(String title, int titleLength) {
        String hack = "HackForTest";
        StringBuilder builder = new StringBuilder(title);
        builder.insert(titleLength, hack);
        ParseState state = ParseState.of(builder.toString());
        state.remove(titleLength, hack.length());
        return state;
    }
}