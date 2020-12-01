package com.github.cinema.home.client.server.torrent.title;

import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
@SpringBootTest(classes = TestContext.class)
public class TorrentTitleParser4ShowsIT {
    @Autowired
    private TorrentTitleParser objectUnderTest;

    @ClassRule
    public static final SpringClassRule scr = new SpringClassRule();
    @Rule
    public final SpringMethodRule smr = new SpringMethodRule();

    @Parameterized.Parameter(value = 0)
    public String torrentTitle;
    @Parameterized.Parameter(value = 1)
    public String mediaTitle;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{"E18.HDRip-ANO", "E18"});
        params.add(new Object[]{"Prison.Break.HDRip-ANOE19", "Prison Break"});
        params.add(new Object[]{"Big.Bang.Theory.S01E18.HDRip-ANO", "Big Bang Theory"});
        params.add(new Object[]{"Big.Bang.Theory.S1E18.HDRip-ANO", "Big Bang Theory"});
        params.add(new Object[]{"Big.Bang.Theory.E18.HDRip-ANO", "Big Bang Theory"});
        params.add(new Object[]{"Big.Bang.Theory E18", "Big Bang Theory"});
        params.add(new Object[]{"Big.Bang.Theory.E18.HDRip-ANOE19", "Big Bang Theory"});
        params.add(new Object[]{"Gilmore.Girls.The.Complete.S01-S07.DVDRip.Dual.XviD-vermi", "Gilmore Girls"});
        params.add(new Object[]{"Vakáción a Mézga család E01-E13", "Vakáción a Mézga család"});
        params.add(new Object[]{"Második esély E065", "Második esély"});
        params.add(new Object[]{"A hegyi doktor - Újra rendel S12E08", "A hegyi doktor - Újra rendel"});
        params.add(new Object[]{"Jamie Oliver, a pucér szakács S01", "Jamie Oliver, a pucér szakács"});
        params.add(new Object[]{"Joban.Rosszban.2020.08.11.WEB-DLRip.x264.AAC2.0.Hun-TheMilkyWay", "Joban Rosszban"});
        params.add(new Object[]{"Brave.CF.38.720p.WEB.DL.x264-ThS", "Brave CF 38"});
        params.add(new Object[]{"A.szerelem.ize.Complete.Read.NFO.720p.WEB-DL.H264.AAC2.0.Hun-TheMilkyWay", "A szerelem ize"});
        params.add(new Object[]{"Suits - S06 - The Complete 6th Season [ 4 BD ] 2016-2017 Blu-ray AVC DTS-HD MA5.1 - d69a74", "Suits"});
        params.add(new Object[]{"The.A-Team.The.Complete.Series.DVDRip.XviD.Hun-HDTV", "The A-Team"});
        params.add(new Object[]{"Dragon.Ball.Complete.Collection.DVDRip.HunDub-Aszpoker", "Dragon Ball"});
        params.add(new Object[]{"Tex Avery's Droopy - The Complete Theatrical Collection", "Tex Avery's Droopy - The Complete Theatrical Collection"});
        params.add(new Object[]{"Vikings.S03.COMPLETE.EXTENDED.WEBRip.x264.HUN-SLN", "Vikings"});
        params.add(new Object[]{"Sons.of.Anarchy.S05.Complete.EXTENDED.GERMAN.DUBBED.BDRip.x264-TVP", "Sons of Anarchy"});
        params.add(new Object[]{"Money.Heist.S01.NF.WEBRip.x264.HUN.SPA.ENG-SFY", "Money Heist"});
        params.add(new Object[]{"South Park.S24E01.Pandemic.Special.720p.HMAX.WEB-DL.DD5.1.H.264.HUN.ENG-pcroland", "South Park"});
        params.add(new Object[]{"South Park - S19 - The Complete 19th Season [ 2 BD ] 2015 Blu-ray AVC True HD5.1 - d69a74", "South Park"});
        return params;
    }

    @Test
    public void givenTorrentTitleAndMediaTitle_whenParseTorrentTitle_assertMediaTitleReturned() {
        TitleNfo result = this.objectUnderTest.parse(this.torrentTitle);
        Assert.assertEquals(this.mediaTitle, result.getMediaTitle());
    }
}
