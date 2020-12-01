package com.github.cinema.home.client.server.common.types.tech;

public enum ResolutionDefinition {
    SD(345600, 24.83),
    HD(1497600, 35.67),
    UHD(8294400, 58.5);

    public final int avgPixelsCount;
    public final double avgPixelQualityScore;

    ResolutionDefinition(int avgPixelsCount, double avgPixelQualityScore) {
        this.avgPixelsCount = avgPixelsCount;
        this.avgPixelQualityScore = avgPixelQualityScore;
    }
}
