package com.github.cinema.home.client.server.common.types.tech;

import java.util.regex.Pattern;

public enum Edition {
    EXTENDED("([. \\]](EE|EXTENDED(?:.CUT|.EDITION)?))(?:\\W|$)"),
    SPECIAL("([. \\]](SPECIAL.EDITION))(?:\\W|$)"),
    DIRECTORS("([. \\]](DD?C|DIRECTORS.CUT))(?:\\W|$)"),
    THEATRICAL("([. \\]](Theatrical(?! Collection)(?:.CUT)?))(?:\\W|$)");

    public final Pattern pattern;

    Edition(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }
}
