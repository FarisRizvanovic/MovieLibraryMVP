package com.example.movieapiapplication;

import com.example.movieapiapplication.models.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("discover/movie")
    Call<Main> getMovies(@Query("api_key") String apiKey,
                         @Query("page") int pageNumber);

    @GET("search/movie")
    Call<Main> getSearch(@Query("api_key") String apiKey,
                         @Query("query") String movieName);
}
