package com.tutorial.phant.flicks.api;

import com.tutorial.phant.flicks.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by phant on 17-Jun-17.
 */

public interface TrailerApi {
    @GET("{id}/trailers")
    Call<Trailers> trailers(@Path("id") String id);
}
