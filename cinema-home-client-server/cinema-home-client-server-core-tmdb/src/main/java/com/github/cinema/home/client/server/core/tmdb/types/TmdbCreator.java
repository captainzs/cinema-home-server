package com.github.cinema.home.client.server.core.tmdb.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbCreator {
    private final int id;
    private final String creditId;
    private final String name;
    private final int gender;
    private final String profilePath;
    private final String job;

    @JsonCreator
    private TmdbCreator(
            @JsonProperty("id") int id,
            @JsonProperty("credit_id") String creditId,
            @JsonProperty("name") String name,
            @JsonProperty("gender") int gender,
            @JsonProperty("profile_path") String profilePath,
            @JsonProperty("job") String job) {
        this.id = id;
        this.creditId = creditId;
        this.name = name;
        this.gender = gender;
        this.profilePath = profilePath;
        this.job = job;
    }

    public int getId() {
        return this.id;
    }

    public String getCreditId() {
        return this.creditId;
    }

    public String getName() {
        return this.name;
    }

    public int getGender() {
        return this.gender;
    }

    public String getProfilePath() {
        return this.profilePath;
    }

    public String getJob() {
        return this.job;
    }

    @Override
    public String toString() {
        return "TmdbCreator{" +
                "id=" + this.id +
                ", creditId='" + this.creditId + '\'' +
                ", name='" + this.name + '\'' +
                ", gender=" + this.gender +
                ", profilePath='" + this.profilePath + '\'' +
                ", job='" + this.job + '\'' +
                '}';
    }
}
