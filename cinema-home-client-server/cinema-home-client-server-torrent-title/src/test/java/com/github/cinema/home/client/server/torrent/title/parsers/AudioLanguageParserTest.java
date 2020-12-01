package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.common.types.tech.Language;
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
public class AudioLanguageParserTest {
    private AudioLanguageParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new AudioLanguageParser();
    }

    @Test
    public void givenHun() {
        ParseState state = this.progressedState("Murder.by.Death.1976.BD50.AVC.DTSHD.HUN.2.0-TRiNiTY", 16);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Language.HUNGARIAN), result.getAudioLanguages());
        Assert.assertEquals(Collections.singleton("2.0"), result.getAudioChannels());
        Assert.assertEquals("Murder.by.Death.1976.BD50.AVC.DTSHD-TRiNiTY", state.getRemaining());
    }

    @Test
    public void given3xHun() {
        ParseState state = this.progressedState("Cyborg.2.1993.WEB-DL.1080p.DD2.0.x264.3xHUN-GuRF", 9);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Language.HUNGARIAN), result.getAudioLanguages());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Cyborg.2.1993.WEB-DL.1080p.DD2.0.x264-GuRF", state.getRemaining());
    }

    @Test
    public void givenGerman_And_HunNarrator() {
        ParseState state = this.progressedState("Red.Scorpion.1988.German.UNRATED.BDRip.DD2.0.x264.HUNNARRATOR-GuRF", 17);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Language.HUNGARIAN, Language.GERMAN), result.getAudioLanguages());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Red.Scorpion.1988.UNRATED.BDRip.DD2.0.x264-GuRF", state.getRemaining());
    }

    @Test
    public void givenHunDub() {
        ParseState state = this.progressedState("Ejszaka.a.muzeumban.HunDub.PAL.DVDR-Zso", 17);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(Collections.singleton(Language.HUNGARIAN), result.getAudioLanguages());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("Ejszaka.a.muzeumban.PAL.DVDR-Zso", state.getRemaining());
    }

    @Test
    public void givenHun_And_Eng() {
        ParseState state = this.progressedState("The.Last.of.the.Mohicans.1992.DDC.720p.BluRay.2xHUN.ENG.x264-Eastwood", 17);
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertEquals(this.asSet(Language.HUNGARIAN, Language.ENGLISH), result.getAudioLanguages());
        Assert.assertNull(result.getAudioChannels());
        Assert.assertEquals("The.Last.of.the.Mohicans.1992.DDC.720p.BluRay.x264-Eastwood", state.getRemaining());
    }

    private Set<Language> asSet(Language... languages) {
        return new HashSet<>(Arrays.asList(languages));
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
