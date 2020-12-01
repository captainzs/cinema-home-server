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
public class TorrentTitleParser4MoviesIT {
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
        params.add(new Object[]{"Exterminators.of.the.year.3000.1983.READ.NFO.1080p.BluRay.DTS.x264.HUN-GS88", "Exterminators of the year 3000"});
        params.add(new Object[]{"2012.2009.BD25.AVC.DTSHD.HUN.x264-TRiNiTY", "2012"});
        params.add(new Object[]{"Room.237.2012.720p.WEB-DL.x264-mSD", "Room 237"});
        params.add(new Object[]{"Endless.2020.1080p.WEB-DL.DD5.1.H.264-FGT", "Endless"});
        params.add(new Object[]{"Project.Power.2020.1080p.NF.WEB-DL.DDP5.1.Atmos.HEVC.HDR.HUN.ENG-PTHD", "Project Power"});
        params.add(new Object[]{"Poltergeist.2015.EXTENDED.CUT.2160p.WEB.H265-PETRiFiED", "Poltergeist"});
        params.add(new Object[]{"The.Protector.2.2013.HC.DVDRip.XviD-AQOS", "The Protector 2"});
        params.add(new Object[]{"Poltergeist.2015.EXTENDED.CUT.2160p.WEB.H265-PETRiFiED", "Poltergeist"});
        params.add(new Object[]{"Broken.Arrow.1996.WS.HUN.PAL.MULTISUBS.DVDR-OHP", "Broken Arrow"});
        params.add(new Object[]{"Star.Wars.Episode.IX.The.Rise.of.Skywalker.2019.1080p.3D.BluRay.Half-SBS.x264.TrueHD.7.1.Atmos-FGT", "Star Wars Episode IX The Rise of Skywalker"});
        params.add(new Object[]{"Tomb.Raider.2018.1080p.UHD.BluRay.DD+7.1.HDR.x265.HuN-TRiNiTY", "Tomb Raider"});
        params.add(new Object[]{"Gengszter.1997.Custom.Hun.NTSC.DVDR-NBTIMI", "Gengszter"});
        params.add(new Object[]{"South.Park.Imaginationland.2008.Directors.Cut.WEBRip.x264.HUN.ENG-Z13", "South Park Imaginationland"});
        params.add(new Object[]{"Rabbit.Without.Ears.2.2009.BDRip.DD2.0.x264.HUN-ADM", "Rabbit Without Ears 2"});
        params.add(new Object[]{"The.Ten.Commandments.1956.HUN.NARRATOR.VHSRIP.READNFO.x264-X911", "The Ten Commandments"});
        params.add(new Object[]{"Eperke és barátai 13. - Vidám főzőcske", "Eperke és barátai 13. - Vidám főzőcske"});
        params.add(new Object[]{"Eperke és barátai 15.", "Eperke és barátai 15."});
        params.add(new Object[]{"RocknRolla[2008]DvDrip-aXXo", "RocknRolla"});
        params.add(new Object[]{"Karmok.harca.1994.HUN.OLVASS.NFO.DVDR.PAL.CUSTOM-Angel666", "Karmok harca"});
        params.add(new Object[]{"A.nagy.kekseg.1988.DUAL.HUN.DVD9.NTSC.CUSTOM-Angel666", "A nagy kekseg"});
        params.add(new Object[]{"The.Looney.Looney.Looney.Bugs.Bunny.Movie.1981.RETAiL.HUN.PAL.DVD9-ISO", "The Looney Looney Looney Bugs Bunny Movie"});
        params.add(new Object[]{"Like Father.Like Son.2013.MTVA.WEB-DLRip.DD2.0.x264.HUN-ADM", "Like Father Like Son"});
        params.add(new Object[]{"Pitch.Black.Theatrical.Cut.[2000].2160p.UHD.BDRemux.HEVC.HDR.DTS-HD.5.1.HuN -d69a74", "Pitch Black"});
        params.add(new Object[]{"Truth.or.Dare.2018.UNRATED.1080p.REMUX.REPACK.BluRay.DTS-HD.MA.5.1.AVC.HuN-TRiNiTY", "Truth or Dare"});
        params.add(new Object[]{"Legacy.of.Lies.2020.AMZN.WEBRip.DD2.0.x264.HuN-BaKeR", "Legacy of Lies"});
        params.add(new Object[]{"Kholop.2019.UNRATED.1080p.WEB-DL.RUS.DD5.1.HUN.x264.SirSzaal", "Kholop"});
        params.add(new Object[]{"The.Hunt.2020.HUN.BDRiP.x264-Gianni", "The Hunt"});
        params.add(new Object[]{"6.Underground.2019.WEBRip.x264.HuN-No1", "6 Underground"});
        params.add(new Object[]{"Veszett.1977.COMPLETE.RETAIL.HUN.PAL.2XDVD5-TRASH", "Veszett"});
        params.add(new Object[]{"The.Drawn.Together.Movie.The.Movie.2010.720p.WEB-DL.AAC2.0.H.264-DNR", "The Drawn Together Movie The Movie"});
        params.add(new Object[]{"Complete.Unknown.2016.CUSTOM.BDRip.x264.HuN-CRLS", "Complete Unknown"});
        params.add(new Object[]{"Overlord.2018.1080p.1080p.UHD.BluRay.DD+7.1.HDR.x265.HuN-No1", "Overlord"});
        return params;
    }

    @Test
    public void givenTorrentTitleAndMediaTitle_whenParseTorrentTitle_assertMediaTitleReturned() {
        TitleNfo result = this.objectUnderTest.parse(this.torrentTitle);
        Assert.assertEquals(this.mediaTitle, result.getMediaTitle());
    }
}
