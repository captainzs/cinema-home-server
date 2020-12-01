package com.github.cinema.home.client.server.core.bll.comparators;

import com.github.cinema.home.client.server.common.types.TorrentNfo;

import java.util.Comparator;

public class SeedsComparator implements Comparator<TorrentNfo> {
    private static final double SEEDERS_MIN = 10;

    @Override
    public int compare(TorrentNfo o1, TorrentNfo o2) {
        if (o1.getSeeds() < SEEDERS_MIN && o2.getSeeds() >= SEEDERS_MIN) {
            return 1;
        } else if (o1.getSeeds() >= SEEDERS_MIN && o2.getSeeds() < SEEDERS_MIN) {
            return -1;
        }
        return 0;
    }
}
