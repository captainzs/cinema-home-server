package com.github.cinema.home.client.server.common.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.cinema.home.client.server.common.types.media.ReleaseNfo;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TorrentNfo {
    private final ReleaseNfo release;
    private final String id;
    private final String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date uploadDate;
    private final double fameRate;
    private final int seeds;
    private final int leechers;
    private final String uploader;

    private TorrentNfo(ReleaseNfo release, String id, String title, Date uploadDate, double fameRate, int seeds,
                       int leechers, String uploader) {
        this.release = release;
        this.id = id;
        this.title = title;
        this.uploadDate = uploadDate;
        this.fameRate = fameRate;
        this.seeds = seeds;
        this.leechers = leechers;
        this.uploader = uploader;
    }

    public ReleaseNfo getRelease() {
        return this.release;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getUploadDate() {
        return this.uploadDate;
    }

    public double getFameRate() {
        return this.fameRate;
    }

    public int getSeeds() {
        return this.seeds;
    }

    public int getLeechers() {
        return this.leechers;
    }

    public String getUploader() {
        return this.uploader;
    }

    @Override
    public String toString() {
        return "TorrentNfo{" +
                "id='" + this.id + '\'' +
                ", title='" + this.title + '\'' +
                ", release=" + this.release + '\'' +
                ", uploadDate=" + this.uploadDate +
                ", fameRate=" + this.fameRate +
                ", seeds=" + this.seeds +
                ", leechers=" + this.leechers +
                ", uploader='" + this.uploader + '\'' +
                '}';
    }

    public static TorrentNfo.Builder builder() {
        return new TorrentNfo.Builder();
    }

    public static TorrentNfo.Builder builder(TorrentNfo copy) {
        return new TorrentNfo.Builder(copy);
    }

    public static class Builder {
        private ReleaseNfo release;
        private String id;
        private String title;
        private Date uploadDate;
        private double fameRate;
        private int seeds;
        private int leechers;
        private String uploader;

        private Builder() {
        }

        private Builder(TorrentNfo copy) {
            this.release = copy.release;
            this.id = copy.id;
            this.title = copy.title;
            this.uploadDate = copy.uploadDate;
            this.fameRate = copy.fameRate;
            this.seeds = copy.seeds;
            this.leechers = copy.leechers;
            this.uploader = copy.uploader;
        }

        public TorrentNfo.Builder release(ReleaseNfo release) {
            this.release = release;
            return this;
        }

        public TorrentNfo.Builder id(String id) {
            this.id = id;
            return this;
        }

        public TorrentNfo.Builder title(String title) {
            this.title = title;
            return this;
        }

        public TorrentNfo.Builder uploadDate(Date uploadDate) {
            this.uploadDate = uploadDate;
            return this;
        }

        public TorrentNfo.Builder fameRate(Double fameRate) {
            this.fameRate = fameRate;
            return this;
        }

        public TorrentNfo.Builder seeds(Integer seeds) {
            this.seeds = seeds;
            return this;
        }

        public TorrentNfo.Builder leechers(Integer leechers) {
            this.leechers = leechers;
            return this;
        }

        public TorrentNfo.Builder uploader(String uploader) {
            this.uploader = uploader;
            return this;
        }

        public final TorrentNfo build() {
            return new TorrentNfo(this.release, this.id, this.title, this.uploadDate, this.fameRate, this.seeds,
                    this.leechers, this.uploader);
        }
    }
}
