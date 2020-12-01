package com.github.cinema.home.client.server.core.bll.comparators;

import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;

import java.util.Comparator;

public class QualityComparator implements Comparator<TorrentNfo> {
    private static final double CLOSENESS_PERCENTAGE = 0.9;
    private final ResolutionDefinition minimumResDef;

    public QualityComparator(ResolutionDefinition minimumResDef) {
        this.minimumResDef = minimumResDef;
    }

    @Override
    public int compare(TorrentNfo o1, TorrentNfo o2) {
        ResolutionDefinition resDef1 = o1.getRelease().getResolutionDefinition();
        ResolutionDefinition resDef2 = o2.getRelease().getResolutionDefinition();
        boolean isResDefEnough1 = resDef1.ordinal() >= this.minimumResDef.ordinal();
        boolean isResDefEnough2 = resDef2.ordinal() >= this.minimumResDef.ordinal();
        if ((isResDefEnough1 && isResDefEnough2) || (!isResDefEnough1 && !isResDefEnough2 && resDef1 == resDef2)) {
            return this.compareSimilars(o1, o2);
        } else if (!isResDefEnough1 && !isResDefEnough2) {
            return (Math.abs(this.minimumResDef.ordinal() - resDef1.ordinal())) <
                    (Math.abs(this.minimumResDef.ordinal() - resDef2.ordinal())) ? -1 : 1;
        }
        return -1 * Boolean.compare(isResDefEnough1, isResDefEnough2);
    }

    private int compareSimilars(TorrentNfo o1, TorrentNfo o2) {
        double qualityScore1 = (double) (o1.getSeeds() + o1.getLeechers()) / this.downloadableFileSize(o1);
        double qualityScore2 = (double) (o2.getSeeds() + o2.getLeechers()) / this.downloadableFileSize(o2);
        double closenessPercentage = Math.min(qualityScore1, qualityScore2) / Math.max(qualityScore1, qualityScore2);
        if (closenessPercentage >= CLOSENESS_PERCENTAGE) {
            int res1 = o1.getRelease().getResolution() != null ? o1.getRelease().getResolution().ordinal() : -1;
            int res2 = o2.getRelease().getResolution() != null ? o2.getRelease().getResolution().ordinal() : -1;
            if (res1 == res2) {
                return -1 * Double.compare(qualityScore1, qualityScore2);
            } else {
                return res1 > res2 ? -1 : 1;
            }
        } else {
            return qualityScore1 > qualityScore2 ? -1 : 1;
        }
    }

    protected long downloadableFileSize(TorrentNfo torrent) {
        return torrent.getRelease().getFullSize();
    }
}
