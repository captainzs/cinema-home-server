package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CDNumParserTest {
    private CDCountParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new CDCountParser();
    }

    @Test
    public void given2Cd() {
        ParseState state = ParseState.of("Jurassic.Park.III.2001.XviD.AC3.2CD-WAF");
        this.objectUnderTest.parseOut(state);
        Assert.assertEquals("Jurassic.Park.III.2001.XviD.AC3-WAF", state.getRemaining());
    }

    @Test
    public void given3Cd() {
        ParseState state = ParseState.of("Jurassic.Park.1993.PROPER.XviD.AC3.3CD-WAF");
        this.objectUnderTest.parseOut(state);
        Assert.assertEquals("Jurassic.Park.1993.PROPER.XviD.AC3-WAF", state.getRemaining());
    }
}
