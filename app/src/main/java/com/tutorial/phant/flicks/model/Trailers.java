package com.tutorial.phant.flicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phant on 17-Jun-17.
 */

public class Trailers {
    @SerializedName("youtube")
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
