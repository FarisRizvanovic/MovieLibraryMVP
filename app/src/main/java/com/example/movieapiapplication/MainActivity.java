package com.example.movieapiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapiapplication.Models.Main;
import com.example.movieapiapplication.Models.ResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "28f8209c67d05e7e61f499ead15f1f51";

    private Button btnSearch;

    private EditText etMovieName;

    private Button btnPreviousPage, btnNextPage;

    private TextView txtPageNumber;

    private Retrofit retrofit;
    private MovieApi movieApi;

    private RecyclerView recyclerView;

    public int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();

        getMovies(null);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = etMovieName.getText().toString();
                if (movieName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Search box is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                getSearch(movieName);
            }
        });

        etMovieName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String movieName = etMovieName.getText().toString();
                    if (movieName.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Search box is empty!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    getSearch(movieName);
                    return true;
                }
                return false;
            }
        });


        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                getMovies(pageNumber);
            }
        });

        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNumber>1){
                    pageNumber--;
                    getMovies(pageNumber);
                }
            }
        });

    }


    private void initViews() {
        recyclerView = findViewById(R.id.moviesRecView);

        btnSearch = findViewById(R.id.btnSearch);

        etMovieName = findViewById(R.id.etMovieName);

        btnNextPage = findViewById(R.id.btnNextPage);
        btnPreviousPage = findViewById(R.id.btnPreviousPage);

        txtPageNumber = findViewById(R.id.txtPageNumber);

    }

    private void getMovies(Integer numberOfPage) {

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
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Main main = response.body();
                List<ResultsItem> movies = main.getResults();

                String pageNumber = "Page: " + main.getPage();
                txtPageNumber.setText(pageNumber);

                MovieRecViewAdapter adapter = new MovieRecViewAdapter(MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter.setMovies(movies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getSearch(String movieName) {

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
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Main main = response.body();
                List<ResultsItem> movies = main.getResults();


                MovieRecViewAdapter adapter = new MovieRecViewAdapter(MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter.setMovies(movies);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}