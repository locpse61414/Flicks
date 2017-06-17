package com.tutorial.phant.flicks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phant on 16-Jun-17.
 */

public class Movie implements Parcelable{
    private static final String POST_FIX = "https://image.tmdb.org/t/p/w500/";
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdroPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("popularity")
    private String popularity;

    public Movie(String id, String title, String posterPath, String backdroPath, String overview, String releaseDate, String voteAverage, String popularity) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdroPath = backdroPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdroPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        popularity = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPopularity() {
        return popularity;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return POST_FIX + posterPath;
    }

    public String getBackdrop_path() {
        return POST_FIX + backdroPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return releaseDate;
    }

    public String getVote_average() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdroPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(popularity);
    }
}
