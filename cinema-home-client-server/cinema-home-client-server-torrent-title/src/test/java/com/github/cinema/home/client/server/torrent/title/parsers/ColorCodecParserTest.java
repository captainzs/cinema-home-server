package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.ColorCodec;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ColorCodecParserTest {
    private ColorCodecParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new ColorCodecParser();
    }

    @Test
    public void givenSecam() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Igy.keszult-A.Birodalom.visszavag.HUN.MTV.Valtozat.SECAM.x264-X911"));
        Assert.assertEquals(ColorCodec.SECAM, result.getColorCodec());
    }

    @Test
    public void givenPal() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("Stavisky.1974.Custom.PAL.DVD9.HUN-Eastwood72"));
        Assert.assertEquals(ColorCodec.PAL, result.getColorCodec());
    }

    @Test
    public void givenNtsc() {
        TitleNfo result = this.objectUnderTest.parseOut(ParseState.of("13.Assassins.2010.CUSTOM.HUNSUB.NTSC.DVD9-VHS"));
        Assert.assertEquals(ColorCodec.NTSC, result.getColorCodec());
    }
}
