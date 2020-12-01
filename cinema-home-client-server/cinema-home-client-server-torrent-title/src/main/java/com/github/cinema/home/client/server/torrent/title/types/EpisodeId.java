package com.github.cinema.home.client.server.torrent.title.types;

import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.types.media.ShowEpisodeNfo;
import com.github.cinema.home.client.server.common.types.media.ShowSeasonNfo;
import org.springframework.data.domain.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EpisodeId {
    private final Integer seasonFrom;
    private final Integer seasonTo;
    private final Integer episodeFrom;
    private final Integer episodeTo;

    public static EpisodeId season(int no) {
        if (no < 0) {
            throw new IllegalArgumentException("Episode number can not be smaller than 0!");
        }
        return new EpisodeId(no, no, null, null);
    }

    public static EpisodeId seasons(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'From' number can not be bigger than 'to' number when creating seasons-id!");
        }
        if (from < 0) {
            throw new IllegalArgumentException("Season number can not be smaller than 0!");
        }
        return new EpisodeId(from, to, null, null);
    }

    public static EpisodeId episode(int no) {
        if (no < 0) {
            throw new IllegalArgumentException("Episode number can not be smaller than 0!");
        }
        return new EpisodeId(null, null, no, no);
    }

    public static EpisodeId episodes(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'From' number can not be bigger than 'to' number when creating episodes-id!");
        }
        if (from < 0) {
            throw new IllegalArgumentException("Episode number can not be smaller than 0!");
        }
        return new EpisodeId(null, null, from, to);
    }

    public static EpisodeId episodes(int seasonNo, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'From' number can not be bigger than 'to' number when creating episodes-id!");
        }
        if (from < 0) {
            throw new IllegalArgumentException("Episode number can not be smaller than 0!");
        }
        return new EpisodeId(seasonNo, seasonNo, from, to);
    }

    private EpisodeId(Integer seasonFrom, Integer seasonTo, Integer episodeFrom, Integer episodeTo) {
        this.seasonFrom = seasonFrom;
        this.seasonTo = seasonTo;
        this.episodeFrom = episodeFrom;
        this.episodeTo = episodeTo;
    }

    public Optional<List<ShowSeasonNfo>> toSeasons(TorrentNfo torrent) {
        if (this.seasonFrom == null || this.seasonTo == null) {
            return Optional.empty();
        }
        List<ShowSeasonNfo> seasons = new ArrayList<>(this.seasonTo - this.seasonFrom + 1);
        for (int seasonNo = this.seasonFrom; seasonNo <= this.seasonTo; seasonNo++) {
            ShowSeasonNfo.Builder season = ShowSeasonNfo.builder().seasonNumber(seasonNo);
            if (this.episodeFrom != null && this.episodeTo != null) {
                for (int episodeNo = this.episodeFrom; episodeNo <= this.episodeTo; episodeNo++) {
                    ShowEpisodeNfo episode = ShowEpisodeNfo.builder()
                            .seasonNumber(seasonNo).episodeNumber(episodeNo).torrent(torrent).build();
                    season.episode(episode);
                }
            } else {
                season.torrent(torrent);
            }
            seasons.add(season.build());
        }
        return Optional.of(seasons);
    }

    public boolean hasHangingEpisodes() {
        return this.seasonFrom == null && this.seasonTo == null && this.episodeFrom != null && this.episodeTo != null;
    }

    public Range<Integer> getSeasons() {
        if (this.seasonFrom == null || this.seasonTo == null) {
            return null;
        }
        return Range.closed(this.seasonFrom, this.seasonTo);
    }

    public Range<Integer> getEpisodes() {
        if (this.episodeFrom == null || this.episodeTo == null) {
            return null;
        }
        return Range.closed(this.episodeFrom, this.episodeTo);
    }
}
