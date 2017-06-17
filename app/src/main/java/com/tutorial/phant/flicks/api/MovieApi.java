package com.tutorial.phant.flicks.api;

import com.tutorial.phant.flicks.model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by phant on 16-Jun-17.
 */

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> nowPlaying(@Query("page") int page);
}
