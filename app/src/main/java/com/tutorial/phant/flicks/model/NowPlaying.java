package com.tutorial.phant.flicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phant on 16-Jun-17.
 */

public class NowPlaying {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
