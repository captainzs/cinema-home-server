package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BlackAndWhiteParserTest {
    private BlackAndWhiteParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new BlackAndWhiteParser();
    }

    @Test
    public void givenNone() {
        ParseState state = this.progressedState("Black.Cat.White.Cat.1998.1080p.WEB-DL.DD5.1.H.264.HUN-GuRF", 20);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.isBlackAndWhite());
        Assert.assertEquals("Black.Cat.White.Cat.1998.1080p.WEB-DL.DD5.1.H.264.HUN-GuRF", state.getRemaining());
    }

    @Test
    public void givenBlack_and_White() {
        ParseState state = this.progressedState("Breakfast.at.Tiffanys.1961.Black.and.White.Bluray.1080p.AC3.2xHUN.CUSTOM.x264-Studio60", 20);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.isBlackAndWhite());
        Assert.assertEquals("Breakfast.at.Tiffanys.1961.Bluray.1080p.AC3.2xHUN.CUSTOM.x264-Studio60", state.getRemaining());
    }

    @Test
    public void givenBlack_White() {
        ParseState state = this.progressedState("Gisaengchung.2019.720p.Black.&.White.BluRay.DD5.1.x264.HuN-TRiNiTY", 20);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.isBlackAndWhite());
        Assert.assertEquals("Gisaengchung.2019.720p.BluRay.DD5.1.x264.HuN-TRiNiTY", state.getRemaining());
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
