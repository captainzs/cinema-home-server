package com.github.cinema.home.client.server.common.types.tech;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

public enum Source {
    CAM_S("([. \\]](?:([2-4])x)?(CAM(?:-?Rip)?))(?:\\W|$)"),
    CAM_H("([. \\]](?:([2-4])x)?(HD-?CAM))(?:\\W|$)"),
    TS_S("([. \\]](?:([2-4])x)?(TELESYNC|TS|PDVD|PreDVDRip))\\W"),
    TS_H("([. \\]](?:([2-4])x)?(HD-?TS))(?:\\W|$)"),
    WP_S("([. \\]](?:([2-4])x)?(WORKPRINT|WP))(?:\\W|$)"),
    VHS_S("([. \\]](?:([2-4])x)?(VHSRip|TELECINE|TC))(?:\\W|$)"),
    VHS_H("([. \\]](?:([2-4])x)?(HDTC|Dtheater(?:-?Rip)?))(?:\\W|$)"),
    PPV_S("([. \\]](?:([2-4])x)?(PPC(?:-?Rip)?))(?:\\W|$)"),
    SCR_S("([. \\]](?:([2-4])x)?((?:VHS-?|DVD-?)?(?:SCREENER|SCR)))(?:\\W|$)"),
    SCR_U("([. \\]](?:([2-4])x)?(B[DR]-?(?:SCREENER|SCR)))(?:\\W|$)"),
    DVDRIP_S("([. \\]](?:([2-4])x)?(LDRip|DVDMux|DVD-?Rip))(?:\\W|$)"),
    DVDRIP_H("([. \\]](?:([2-4])x)?(HD-?(?:DVD-?)?Rip))(?:\\W|$)"),
    DVD_S("([. \\]](?:([2-4])x)?(DVD-?[R59]))(?:\\W|$)"),
    DVD_H("([. \\]](?:([2-4])x)?(HD-?DVD))(?:\\W|$)"),
    TV_S("([. \\]](?:([2-4])x)?(DSRip|VODR|(?:(?:DSR|SAT|DVB-?[CT]?|PDTV|TV|SDTV|VOD|DTH)(?:-?Rip)?)))(?:\\W|$)"),
    TV_H("([. \\]](?:([2-4])x)?(A?HDTV(?:-?Rip)?))(?:\\W|$)"),
    WEB("([. (\\]](?:([2-4])x)?(WEB(?!-?(?:DL|LD))(?:-?Rip)?))(?:\\W|$)"),
    WEBDL("([. \\]](?:([2-4])x)?(WEB-?(?:DL|LD)(?:-?Rip)?))(?:\\W|$)"),
    BLURAY_S("([. \\]](?:([2-4])x)?(BRRip|BRip|BDRip))(?:\\W|$)"),
    BLURAY_H("([. \\]](?:([2-4])x)?(REMUX|Blu-?Ray))(?:\\W|$)"),
    BLURAY_U("([.\\[\\] ]+(?:([2-4])[x ])?(?:([2-4])x)?(BD(?:5|9|25|50|-?Remux)?))(?:\\W|$)");

    public final Pattern pattern;

    Source(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
