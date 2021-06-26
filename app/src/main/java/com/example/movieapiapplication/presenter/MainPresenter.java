package com.example.movieapiapplication.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapiapplication.MovieApi;
import com.example.movieapiapplication.activities.MainActivity;
import com.example.movieapiapplication.adapters.MovieRecViewAdapter;
import com.example.movieapiapplication.models.Main;
import com.example.movieapiapplication.models.ResultsItem;
import com.example.movieapiapplication.views.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "28f8209c67d05e7e61f499ead15f1f51";

    private Retrofit retrofit;
    private MovieApi movieApi;

    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void getMovies(Integer numberOfPage, Context context) {

        if (numberOfPage == null) {
            numberOfPage = 1;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);

        Call<Main> call = movieApi.getMovies(API_KEY, numberOfPage);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Main main = response.body();
                mainView.getMovies(main);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getSearch(String movieName, Context context) {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);

        Call<Main> call = movieApi.getSearch(API_KEY, movieName);
        call.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                if (!response.isSuccessful()) {
                    mainView.onError("Code" + response.code());
                    return;
                }
                Main main = response.body();
                mainView.getSearch(main);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                mainView.onError(t.getMessage());
            }
        });
    }
}
