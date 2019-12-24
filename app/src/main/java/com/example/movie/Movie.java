package com.example.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("title")
    @Expose
    private String original_title;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String poster_path;

   /* public Movie(String original_title, String overview) {
        this.original_title = original_title;
        this.overview = overview;
    }*/

    public Movie(String original_title, String overview, String poster_path) {
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String setPoster_path(String poster_path) {
       return this.poster_path = poster_path;

    }
}

