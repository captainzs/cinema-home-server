package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TmdbCompany {
    private final int id;
    private final String logoPath;
    private final String name;
    private final String originCountry;

    public static TmdbCompany of(int id, String name) {
        return new TmdbCompany(id, null, name, null);
    }

    @JsonCreator
    private TmdbCompany(
            @JsonProperty("id") int id,
            @JsonProperty("logo_path") String logoPath,
            @JsonProperty("name") String name,
            @JsonProperty("origin_country") String originCountry) {
        this.id = id;
        this.logoPath = logoPath;
        this.name = name;
        this.originCountry = originCountry;
    }

    public int getId() {
        return this.id;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public String getName() {
        return this.name;
    }

    public String getOriginCountry() {
        return this.originCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        TmdbCompany tmdbCompany = (TmdbCompany) o;
        return this.id == tmdbCompany.id &&
                Objects.equals(this.name, tmdbCompany.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "TmdbCompany{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                '}';
    }
}
