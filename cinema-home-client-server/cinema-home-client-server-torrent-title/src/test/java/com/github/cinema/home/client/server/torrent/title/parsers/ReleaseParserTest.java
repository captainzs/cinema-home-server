package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(JUnit4.class)
public class ReleaseParserTest {
    private ReleaseParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new ReleaseParser();
    }

    @Test
    public void givenDateWithHyphens() throws ParseException {
        ParseState state = ParseState.of("Boxing.Tysonl.vs. Jones.2020-11-28.720p HDTV.AAC.H264-Ali");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.fromString("2020.11.28"), result.getReleaseDate());
        Assert.assertNull(result.getReleaseYears());
        Assert.assertEquals("Boxing.Tysonl.vs. Jones.720p HDTV.AAC.H264-Ali", state.getRemaining());
    }

    private Date fromString(String date) throws ParseException {
        return new SimpleDateFormat("yyyy.MM.dd").parse(date);
    }
}
