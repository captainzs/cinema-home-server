package com.github.cinema.home.client.server.torrent.title;

import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;

public class ParseState {
    public final TitleNfo.Builder properties;
    private final StringBuilder remaining;
    private int removalMinIndex;

    public static ParseState of(String original) {
        return new ParseState(original);
    }

    private ParseState(String original) {
        this.properties = TitleNfo.builder().torrentTitle(original);
        this.remaining = new StringBuilder(original);
        this.removalMinIndex = original.length();
    }

    public String remove(int from, int length) {
        if (from < 0 || from >= this.remaining.length()) {
            throw new IllegalArgumentException("Can not remove characters from index: " + from);
        } else if (from < this.removalMinIndex) {
            this.removalMinIndex = from;
        }
        return this.remaining.replace(from, from + length, "").toString();
    }

    public String getRemaining() {
        return this.remaining.toString();
    }

    public int getRemovalMinIndex() {
        return this.removalMinIndex;
    }
}
