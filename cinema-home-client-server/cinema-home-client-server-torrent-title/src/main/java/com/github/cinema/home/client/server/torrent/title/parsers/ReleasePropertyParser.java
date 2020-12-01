package com.github.cinema.home.client.server.torrent.title.parsers;

import com.github.cinema.home.client.server.torrent.title.ParseState;
import com.github.cinema.home.client.server.torrent.title.types.TitleNfo;

public interface ReleasePropertyParser {
    TitleNfo parseOut(ParseState mutableState);
}
