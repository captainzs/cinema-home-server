package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CompleteParserTest {
    private CompleteParser objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new CompleteParser();
    }

    @Test
    public void givenComplete() {
        ParseState state = ParseState.of("A.szerelem.ize.Complete.Read.NFO.720p.WEB-DL.H264.AAC2.0.Hun-TheMilkyWay");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.isComplete());
        Assert.assertEquals("A.szerelem.ize.Read.NFO.720p.WEB-DL.H264.AAC2.0.Hun-TheMilkyWay", state.getRemaining());
    }

    @Test
    public void givenTheCompleteSeason_assertNothingParsedBecauseThatsAnotherParsersJob() {
        ParseState state = ParseState.of("Suits - S06 - The Complete 6th Season [ 4 BD ] 2016-2017 Blu-ray AVC DTS-HD MA5.1 - d69a74");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.isComplete());
        Assert.assertEquals("Suits - S06 - The Complete 6th Season [ 4 BD ] 2016-2017 Blu-ray AVC DTS-HD MA5.1 - d69a74", state.getRemaining());
    }

    @Test
    public void givenTheCompleteSeries() {
        ParseState state = ParseState.of("The.A-Team.The.Complete.Series.DVDRip.XviD.Hun-HDTV");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.isComplete());
        Assert.assertEquals("The.A-Team.DVDRip.XviD.Hun-HDTV", state.getRemaining());
    }

    @Test
    public void givenTheCompleteCollection() {
        ParseState state = ParseState.of("Dragon.Ball.Complete.Collection.DVDRip.HunDub-Aszpoker");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertTrue(result.isComplete());
        Assert.assertEquals("Dragon.Ball.DVDRip.HunDub-Aszpoker", state.getRemaining());
    }

    @Test
    public void givenTheCompleteTheatricalCollection_assertNotParsedBecauseThisIsTheTitle() {
        ParseState state = ParseState.of("Tex Avery's Droopy - The Complete Theatrical Collection");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.isComplete());
        Assert.assertEquals("Tex Avery's Droopy - The Complete Theatrical Collection", state.getRemaining());
    }

    @Test
    public void givenCompleteAtTheBeginning_assertNotParsed() {
        ParseState state = ParseState.of("Complete.Unknown.2016.CUSTOM.BDRip.x264.HuN-CRLS");
        TitleNfo result = this.objectUnderTest.parseOut(state);
        Assert.assertNull(result.isComplete());
        Assert.assertEquals("Complete.Unknown.2016.CUSTOM.BDRip.x264.HuN-CRLS", state.getRemaining());
    }

    //TODO
    //Dragon.Ball.GT.Full.DVDRip.JapDub.HunSub-Fugitive
}
