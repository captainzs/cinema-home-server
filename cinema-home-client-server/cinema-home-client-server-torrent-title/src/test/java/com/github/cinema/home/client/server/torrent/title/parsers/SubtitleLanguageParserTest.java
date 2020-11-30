package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.Language;
import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collections;

@RunWith(JUnit4.class)
public class SubtitleLanguageParserTest {
    private SubtitleLanguageParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new SubtitleLanguageParser();
    }

    @Test
    public void givenNone_butHasAudioLanguages() {
        ParseState state = ParseState.of("The.Last.of.the.Mohicans.1992.DDC.720p.BluRay.2xHUN.ENG.x264-Eastwood");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.getSubtitleLanguages());
        Assert.assertEquals("The.Last.of.the.Mohicans.1992.DDC.720p.BluRay.2xHUN.ENG.x264-Eastwood", state.getRemaining());
    }

    @Test
    public void givenHunSub() {
        ParseState state = ParseState.of("Alone.2020.1080p.AMZN.WEB-DL.H264.Hunsub-Pirosasz");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Language.HUNGARIAN), result.getSubtitleLanguages());
        Assert.assertEquals("Alone.2020.1080p.AMZN.WEB-DL.H264-Pirosasz", state.getRemaining());
    }

    @Test
    public void givenDkSubs() {
        ParseState state = ParseState.of("The.Kindness.of.Strangers.2019.Retail.DKSubs.1080p.WEB-DL.H.264-NiteCru");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Language.DANISH), result.getSubtitleLanguages());
        Assert.assertEquals("The.Kindness.of.Strangers.2019.Retail.1080p.WEB-DL.H.264-NiteCru", state.getRemaining());
    }
}
