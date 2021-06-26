package com.example.movieapiapplication.views;

import com.example.movieapiapplication.models.Main;
import com.example.movieapiapplication.models.ResultsItem;

import java.util.List;

public interface MainView {

    void getMovies(Main main);
    void getSearch(Main main);
    void onError(String errorMessage);
}
