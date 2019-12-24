package com.example.movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("3/movie/top_rated?")
    Call<Results> movieList1(@Query("api_key") String api_key);
    @GET("3/movie/top_rated?")
    Call<Results> movieList(@Query("api_key") String api_key, @Query("page") Integer page);

}
