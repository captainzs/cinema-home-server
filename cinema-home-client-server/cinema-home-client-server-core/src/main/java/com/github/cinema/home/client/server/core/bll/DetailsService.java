package com.github.cinema.home.client.server.core.bll;

import com.github.cinema.home.client.server.common.exceptions.InvalidArgumentException;
import com.github.cinema.home.client.server.common.exceptions.ServiceErrorException;
import com.github.cinema.home.client.server.common.types.media.ImdbId;
import com.github.cinema.home.client.server.common.types.media.MediaNfo;
import com.github.cinema.home.client.server.common.types.media.MovieNfo;
import com.github.cinema.home.client.server.common.types.media.ShowNfo;
import com.github.cinema.home.client.server.common.types.media.ShowSeasonNfo;
import com.github.cinema.home.client.server.common.types.media.TmdbId;
import com.github.cinema.home.client.server.core.tmdb.TmdbMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailsService {
    private final TmdbMediaService mediaService;
    private final IntegratedMediaExtender extender;

    @Autowired
    public DetailsService(TmdbMediaService mediaService, IntegratedMediaExtender extender) {
        this.mediaService = mediaService;
        this.extender = extender;
    }

    public MovieNfo detailMovie(ImdbId id) {
        return this.extender.extendFull(MovieNfo.builder().imdbId(id).build());
    }

    public ShowNfo detailShow(ImdbId id) {
        return this.extender.extendFull(ShowNfo.builder().imdbId(id).build());
    }

    public ShowNfo detailShowWithSeasons(ImdbId id) throws InvalidArgumentException, ServiceErrorException {
        ShowNfo show = this.extender.extendUntil(ShowNfo.builder().imdbId(id).build(), this::hasSeasonAndEpisodeNumbers);
        List<ShowSeasonNfo> detailedSeasons = new ArrayList<>();
        for (ShowSeasonNfo season : show.getSeasons()) {
            detailedSeasons.add(this.detailSeason(show.getTmdbId(), season.getSeasonNumber()));
        }
        return ShowNfo.builder().seasons(detailedSeasons).build(show);
    }

    public ShowSeasonNfo detailSeason(TmdbId id, int seasonNumber) throws InvalidArgumentException, ServiceErrorException {
        Optional<ShowSeasonNfo> season = this.mediaService.findShowSeason(id, seasonNumber);
        if (!season.isPresent()) {
            throw new InvalidArgumentException(String.format("Season '%s' is not found for show: '%s'", seasonNumber, id));
        }
        return season.get();
    }

    private boolean hasSeasonAndEpisodeNumbers(MediaNfo media) {
        ShowNfo show = (ShowNfo) media;
        return show.getTmdbId() != null && show.getSeasons() != null && show.getSeasons().size() > 0;
    }
}
