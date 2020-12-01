package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.Container;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ContainerParserTest {
    private ContainerParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new ContainerParser();
    }

    @Test
    public void givenNone() {
        ParseState state = this.progressedState("TRON.LEGACY.2010.MVC.3D.BD25.Re-Encoded.DTS-HD.7.1.Hun-HDPhoenix", 12);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getContainer());
        Assert.assertEquals("TRON.LEGACY.2010.MVC.3D.BD25.Re-Encoded.DTS-HD.7.1.Hun-HDPhoenix", state.getRemaining());
    }

    @Test
    public void givenTs() {
        ParseState state = this.progressedState("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org.ts", 15);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Container.TS, result.getContainer());
        Assert.assertEquals("RTE.Jihad.Jane.1080i.HDTV.h264.MP2.MVGroup.org", state.getRemaining());
    }

    @Test
    public void givenMkv() {
        ParseState state = this.progressedState("Ur.Musig.DVD.x264.AC3.MVGroup.Forum.mkv", 15);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Container.MKV, result.getContainer());
        Assert.assertEquals("Ur.Musig.DVD.x264.AC3.MVGroup.Forum", state.getRemaining());
    }

    @Test
    public void givenMp4() {
        ParseState state = this.progressedState("NubileFilms.20.09.18.Mona.Blue.Glad.You.Came.XXX.SD.MP4-KLEENEX", 15);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Container.MP4, result.getContainer());
        Assert.assertEquals("NubileFilms.20.09.18.Mona.Blue.Glad.You.Came.XXX.SD-KLEENEX", state.getRemaining());
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
