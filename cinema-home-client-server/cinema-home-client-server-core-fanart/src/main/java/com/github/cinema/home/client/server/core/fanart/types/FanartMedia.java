package com.github.cinema.home.client.server.core.fanart.types;

import com.github.cinema.home.client.server.common.types.media.MediaNfo;

import java.util.List;

public abstract class FanartMedia {
    protected final String name;
    protected final List<FanartImage> hdClearArts;
    protected final List<FanartImage> hdClearLogos;
    protected final List<FanartImage> posters;
    protected final List<FanartImage> backgrounds;
    protected final List<FanartImage> thumbs;
    protected final List<FanartImage> clearArts;
    protected final List<FanartImage> banners;
    protected final List<FanartImage> clearLogos;

    protected FanartMedia(String name, List<FanartImage> hdClearArts, List<FanartImage> hdClearLogos,
                          List<FanartImage> posters, List<FanartImage> backgrounds, List<FanartImage> thumbs,
                          List<FanartImage> clearArts, List<FanartImage> banners, List<FanartImage> clearLogos) {
        this.name = name;
        this.hdClearArts = hdClearArts;
        this.hdClearLogos = hdClearLogos;
        this.posters = posters;
        this.backgrounds = backgrounds;
        this.thumbs = thumbs;
        this.clearArts = clearArts;
        this.banners = banners;
        this.clearLogos = clearLogos;
    }

    public String getName() {
        return this.name;
    }

    public List<FanartImage> getHdClearArts() {
        return this.hdClearArts;
    }

    public List<FanartImage> getHdClearLogos() {
        return this.hdClearLogos;
    }

    public List<FanartImage> getPosters() {
        return this.posters;
    }

    public List<FanartImage> getBackgrounds() {
        return this.backgrounds;
    }

    public List<FanartImage> getThumbs() {
        return this.thumbs;
    }

    public List<FanartImage> getClearArts() {
        return this.clearArts;
    }

    public List<FanartImage> getBanners() {
        return this.banners;
    }

    public List<FanartImage> getClearLogos() {
        return this.clearLogos;
    }

    public abstract MediaNfo toNfo();
}
