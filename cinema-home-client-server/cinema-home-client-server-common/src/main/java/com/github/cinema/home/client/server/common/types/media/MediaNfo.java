package com.github.cinema.home.client.server.common.types.media;

import com.github.cinema.home.client.server.common.types.TorrentNfo;
import com.github.cinema.home.client.server.common.utils.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;

public abstract class MediaNfo {
    protected final ImdbId imdbId;
    protected final TmdbId tmdbId;
    protected final TvdbId tvdbId;

    protected final LinkedHashSet<LocaleString> titles;
    protected final LinkedHashSet<Genre> genres;
    protected final LinkedHashSet<LocaleString> plots;
    protected final Double runtime;
    protected final Double rating;
    protected final Integer releaseYear;
    protected final MediaCollection collection;

    protected final LinkedHashSet<NetworkGroup> networks;
    protected final LinkedHashSet<String> creators;
    protected final LinkedHashSet<Actor> actors;

    protected final LinkedHashSet<Image> posters;
    protected final LinkedHashSet<Image> backdrops;
    protected final LinkedHashSet<Image> logos;
    protected final LinkedHashSet<Image> thumbs;
    protected final LinkedHashSet<Video> videos;

    protected MediaNfo(ImdbId imdbId, TmdbId tmdbId, TvdbId tvdbId, LinkedHashSet<LocaleString> titles,
                       LinkedHashSet<Genre> genres, LinkedHashSet<LocaleString> plots, Double runtime, Double rating,
                       Integer releaseYear, MediaCollection collection, LinkedHashSet<NetworkGroup> networks,
                       LinkedHashSet<String> creators, LinkedHashSet<Actor> actors, LinkedHashSet<Image> posterUrls,
                       LinkedHashSet<Image> backgroundUrls, LinkedHashSet<Image> logoUrls, LinkedHashSet<Image> thumbUrls,
                       LinkedHashSet<Video> videos) {
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
        this.tvdbId = tvdbId;
        this.titles = titles;
        this.genres = genres;
        this.plots = plots;
        this.runtime = runtime;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.collection = collection;
        this.networks = networks;
        this.creators = creators;
        this.actors = actors;
        this.posters = posterUrls;
        this.backdrops = backgroundUrls;
        this.logos = logoUrls;
        this.thumbs = thumbUrls;
        this.videos = videos;
    }

    public boolean hasImdbId() {
        return !StringUtils.isEmpty(this.imdbId);
    }

    public boolean hasTmdbId() {
        return !StringUtils.isEmpty(this.tmdbId);
    }

    public boolean hasTvdbId() {
        return !StringUtils.isEmpty(this.tvdbId);
    }

    public abstract boolean hasTorrent();

    public abstract void sortTorrentsBy(Comparator<TorrentNfo> comparator);

    public ImdbId getImdbId() {
        return this.imdbId;
    }

    public TmdbId getTmdbId() {
        return this.tmdbId;
    }

    public TvdbId getTvdbId() {
        return this.tvdbId;
    }

    public LinkedHashSet<LocaleString> getTitles() {
        return this.titles;
    }

    public LinkedHashSet<Genre> getGenres() {
        return this.genres;
    }

    public LinkedHashSet<LocaleString> getPlots() {
        return this.plots;
    }

    public Double getRuntime() {
        return this.runtime;
    }

    public Double getRating() {
        return this.rating;
    }

    public Integer getReleaseYear() {
        return this.releaseYear;
    }

    public MediaCollection getCollection() {
        return this.collection;
    }

    public LinkedHashSet<NetworkGroup> getNetworks() {
        return this.networks;
    }

    public LinkedHashSet<String> getCreators() {
        return this.creators;
    }

    public LinkedHashSet<Actor> getActors() {
        return this.actors;
    }

    public LinkedHashSet<Image> getPosters() {
        return this.posters;
    }

    public LinkedHashSet<Image> getBackdrops() {
        return this.backdrops;
    }

    public LinkedHashSet<Image> getLogos() {
        return this.logos;
    }

    public LinkedHashSet<Image> getThumbs() {
        return this.thumbs;
    }

    public LinkedHashSet<Video> getVideos() {
        return this.videos;
    }

    public abstract static class Builder<T extends MediaNfo, B extends MediaNfo.Builder<T, B>> {
        protected static Logger logger = LoggerFactory.getLogger(MediaNfo.Builder.class);
        protected ImdbId imdbId;
        protected TmdbId tmdbId;
        protected TvdbId tvdbId;
        protected LinkedHashSet<LocaleString> titles;
        protected LinkedHashSet<Genre> genres;
        protected LinkedHashSet<LocaleString> plots;
        protected Double runtime;
        protected Double rating;
        protected Integer releaseYear;
        protected MediaCollection collection;
        protected LinkedHashSet<NetworkGroup> networks;
        protected LinkedHashSet<String> creators;
        protected LinkedHashSet<Actor> actors;
        protected LinkedHashSet<Image> posters;
        protected LinkedHashSet<Image> backdrops;
        protected LinkedHashSet<Image> logos;
        protected LinkedHashSet<Image> thumbs;
        protected LinkedHashSet<Video> videos;

        public B imdbId(ImdbId imdbId) {
            if (imdbId == null) {
                return this.self();
            } else if (this.imdbId != null && !this.imdbId.equals(imdbId)) {
                logger.warn(String.format("Multiple but different imdb ids given! ('%s', '%s')", this.imdbId, imdbId));
            } else {
                this.imdbId = imdbId;
            }
            return this.self();
        }

        public B tmdbId(TmdbId tmdbId) {
            if (tmdbId == null) {
                return this.self();
            } else if (this.tmdbId != null && !this.tmdbId.equals(tmdbId)) {
                logger.warn(String.format("Multiple but different tmdb ids given! ('%s', '%s')", this.tmdbId, tmdbId));
            } else {
                this.tmdbId = tmdbId;
            }
            return this.self();
        }

        public B tvdbId(TvdbId tvdbId) {
            if (tvdbId == null) {
                return this.self();
            } else if (this.tvdbId != null && !this.tvdbId.equals(tvdbId)) {
                logger.warn(String.format("Multiple but different tvdb ids given! ('%s', '%s')", this.tvdbId, tvdbId));
            } else {
                this.tvdbId = tvdbId;
            }
            return this.self();
        }

        public B titles(LinkedHashSet<LocaleString> titles) {
            if (titles == null || titles.isEmpty()) {
                return this.self();
            } else if (this.titles == null) {
                this.titles = new LinkedHashSet<>(titles);
            } else {
                this.titles.addAll(titles);
            }
            return this.self();
        }

        public B title(LocaleString title) {
            if (StringUtils.isEmpty(title)) {
                return this.self();
            }
            return this.titles(Sets.singletonLinkedSet(title));
        }

        public B genres(LinkedHashSet<Genre> genres) {
            if (genres == null || genres.isEmpty()) {
                return this.self();
            } else if (this.genres == null) {
                this.genres = new LinkedHashSet<>(genres);
            } else {
                this.genres.addAll(genres);
            }
            return this.self();
        }

        public B genre(Genre genre) {
            if (genre == null) {
                return this.self();
            }
            return this.genres(Sets.singletonLinkedSet(genre));
        }

        public B plots(LinkedHashSet<LocaleString> plots) {
            if (plots == null || plots.isEmpty()) {
                return this.self();
            } else if (this.plots == null) {
                this.plots = new LinkedHashSet<>(plots);
            } else {
                this.plots.addAll(plots);
            }
            return this.self();
        }

        public B plot(LocaleString plot) {
            if (StringUtils.isEmpty(plot)) {
                return this.self();
            }
            return this.plots(Sets.singletonLinkedSet(plot));
        }

        public B runtime(Double runtime) {
            if (runtime == null) {
                return this.self();
            } else if (this.runtime != null) {
                this.runtime = (this.runtime + runtime) / 2.0;
            } else {
                this.runtime = runtime;
            }
            return this.self();
        }

        public B rating(Double rating) {
            if (rating == null) {
                return this.self();
            } else if (this.rating != null) {
                this.rating = (this.rating + rating) / 2.0;
            } else {
                this.rating = rating;
            }
            return this.self();
        }

        public B releaseYear(Integer releaseYear) {
            if (releaseYear == null) {
                return this.self();
            } else if (this.releaseYear != null && !this.releaseYear.equals(releaseYear) && Math.abs(this.releaseYear - releaseYear) > 1) {
                logger.warn(String.format("Multiple but very different release-years are given! ('%s', '%s') for imdb-id: '%s'", this.releaseYear, releaseYear, this.imdbId));
            } else {
                this.releaseYear = releaseYear;
            }
            return this.self();
        }

        public B collection(MediaCollection collection) {
            if (collection == null) {
                return this.self();
            } else if (this.collection != null && !this.collection.equals(collection)) {
                logger.warn(String.format("Multiple but different collections given! ('%s', '%s')", this.collection, collection));
            } else {
                this.collection = collection;
            }
            return this.self();
        }

        public B networks(LinkedHashSet<NetworkGroup> networks) {
            if (networks == null || networks.isEmpty()) {
                return this.self();
            } else if (this.networks == null) {
                this.networks = new LinkedHashSet<>(networks);
            } else {
                this.networks.addAll(networks);
            }
            return this.self();
        }

        public B network(NetworkGroup network) {
            if (network == null) {
                return this.self();
            }
            return this.networks(Sets.singletonLinkedSet(network));
        }

        public B creators(LinkedHashSet<String> creators) {
            if (creators == null || creators.isEmpty()) {
                return this.self();
            } else if (this.creators == null) {
                this.creators = new LinkedHashSet<>(creators);
            } else {
                this.creators.addAll(creators);
            }
            return this.self();
        }

        public B actors(LinkedHashSet<Actor> actors) {
            if (actors == null || actors.isEmpty()) {
                return this.self();
            } else if (this.actors == null) {
                this.actors = new LinkedHashSet<>(actors);
            } else {
                this.actors.addAll(actors);
            }
            return this.self();
        }

        public B posters(LinkedHashSet<Image> posters) {
            if (posters == null || posters.isEmpty()) {
                return this.self();
            } else if (this.posters == null) {
                this.posters = new LinkedHashSet<>(posters);
            } else {
                this.posters.addAll(posters);
            }
            return this.self();
        }

        public B poster(Image poster) {
            if (poster == null) {
                return this.self();
            }
            return this.posters(Sets.singletonLinkedSet(poster));
        }

        public B backdrops(LinkedHashSet<Image> backdrops) {
            if (backdrops == null || backdrops.isEmpty()) {
                return this.self();
            } else if (this.backdrops == null) {
                this.backdrops = new LinkedHashSet<>(backdrops);
            } else {
                this.backdrops.addAll(backdrops);
            }
            return this.self();
        }

        public B backdrop(Image backdrop) {
            if (backdrop == null) {
                return this.self();
            }
            return this.backdrops(Sets.singletonLinkedSet(backdrop));
        }


        public B logos(LinkedHashSet<Image> logos) {
            if (logos == null || logos.isEmpty()) {
                return this.self();
            } else if (this.logos == null) {
                this.logos = new LinkedHashSet<>(logos);
            } else {
                this.logos.addAll(logos);
            }
            return this.self();
        }

        public B thumbs(LinkedHashSet<Image> thumbs) {
            if (thumbs == null || thumbs.isEmpty()) {
                return this.self();
            } else if (this.thumbs == null) {
                this.thumbs = new LinkedHashSet<>(thumbs);
            } else {
                this.thumbs.addAll(thumbs);
            }
            return this.self();
        }

        public B videos(LinkedHashSet<Video> videos) {
            if (videos == null || videos.isEmpty()) {
                return this.self();
            } else if (this.videos == null) {
                this.videos = new LinkedHashSet<>(videos);
            } else {
                this.videos.addAll(videos);
            }
            return this.self();
        }


        protected B merge(T nfo) {
            return this
                    .imdbId(nfo.getImdbId())
                    .tmdbId(nfo.getTmdbId())
                    .tvdbId(nfo.getTvdbId())
                    .titles(nfo.getTitles())
                    .genres(nfo.getGenres())
                    .plots(nfo.getPlots())
                    .runtime(nfo.getRuntime())
                    .rating(nfo.getRating())
                    .releaseYear(nfo.getReleaseYear())
                    .collection(nfo.getCollection())
                    .networks(nfo.getNetworks())
                    .creators(nfo.getCreators())
                    .actors(nfo.getActors())
                    .posters(nfo.getPosters())
                    .backdrops(nfo.getBackdrops())
                    .logos(nfo.getLogos())
                    .thumbs(nfo.getThumbs())
                    .videos(nfo.getVideos());
        }

        final B self() {
            return (B) this;
        }

        public abstract T build();
    }
}
