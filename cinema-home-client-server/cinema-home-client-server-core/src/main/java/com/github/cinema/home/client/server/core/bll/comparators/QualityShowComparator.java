package com.github.cinema.home.client.server.core.bll.comparators;

import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;

public class QualityShowComparator extends QualityComparator {
    public QualityShowComparator(ResolutionDefinition minimumResDef) {
        super(minimumResDef);
    }

    @Override
    protected long downloadableFileSize(TorrentNfo torrent) {
        return 1;
    }
}
