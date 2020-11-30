package com.github.cinema.home.client.server.common.types.tech;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Pattern;

public enum AudioCodec {
    MP2("([. \\]](MP2)(?:.?([257].[01]))?)(?:\\W|$)"),
    MP3("([. \\]](MP3)(?:.?([257].[01]))?)(?:\\W|$)"),
    AAC("([. \\]](AAC)(?:.?([257].[01]))?)(?:\\W|$)"),
    AC3("([. \\]](AC3|AC-3|DD(?![\\+P]))(?:.?([257].[01]))?)(?:\\W|$)"),
    EAC3("([. \\]](DD\\+|DDP|DDPlus|E-?AC-?3)(?:.?([257].[01]))?(?:.Atmos(?!.?([257].[01])))?)(?:\\W|$)"),
    DTS("([. \\]](DTS(?!-?(?:ES|HD|HR|X)))(?:.?([257].[01]))?)(?:\\W|$)"),
    DTS_ES("([. \\]](DTS-?ES(?:.Matrix)?)(?:.?([257].[01]))?)(?:\\W|$)"),
    FLAC("([. \\]](FLAC)(?:.?([257].[01]))?)(?:\\W|$)"),
    TRUE_HD("([. \\]]((?:Dolby.)?(?:Atmos.TrueHD|TrueHD.Atmos|Atmos|TrueHD)(?:.?([257].[01]))?(?:.Atmos)?))(?:\\W|$)"),
    DTS_HD("([. \\]](DTS-HR|DTS[.-]?HD(?:[ .-]?(?:MA|HRA?))?)(?:.?([257].[01]))?)(?:\\W|$)"),
    DTS_X("([. \\]](DTS-?X(?:[.-]?MA)?)(?:.?([257].[01]))?)(?:\\W|$)"),
    PCM("([. \\]](L?PCM)(?:.?([257].[01]))?)(?:\\W|$)");

    public final Pattern pattern;

    AudioCodec(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
