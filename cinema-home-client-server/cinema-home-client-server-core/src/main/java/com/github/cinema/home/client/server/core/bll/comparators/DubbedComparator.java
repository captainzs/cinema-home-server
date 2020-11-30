package com.github.cinema.home.client.server.core.bll.comparators;

import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.tech.Language;

import java.util.Comparator;
import java.util.Set;

public class DubbedComparator implements Comparator<TorrentNfo> {
    @Override
    public int compare(TorrentNfo o1, TorrentNfo o2) {
        Set<Language> languages1 = o1.getRelease().getAudioLanguages();
        Set<Language> languages2 = o2.getRelease().getAudioLanguages();
        boolean hasPreferredLanguage1 = languages1 != null && languages1.contains(Language.HUNGARIAN);
        boolean hasPreferredLanguage2 = languages2 != null && languages2.contains(Language.HUNGARIAN);
        if (hasPreferredLanguage1 && !hasPreferredLanguage2) {
            return -1;
        } else if (!hasPreferredLanguage1 && hasPreferredLanguage2) {
            return 1;
        }
        return 0;
    }
}
