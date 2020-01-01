package com.example.movie.Apis;

import com.example.movie.Classes.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("3/movie/top_rated?")
    Call<Results> movieList1(@Query("api_key") String api_key);
    @GET("3/movie/top_rated?")
    Call<Results> movieList(@Query("api_key") String api_key, @Query("page") Integer page);

}
