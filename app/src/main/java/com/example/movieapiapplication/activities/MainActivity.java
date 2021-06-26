package com.example.movieapiapplication.activities;

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

import com.example.movieapiapplication.MovieApi;
import com.example.movieapiapplication.R;
import com.example.movieapiapplication.adapters.MovieRecViewAdapter;
import com.example.movieapiapplication.models.Main;
import com.example.movieapiapplication.models.ResultsItem;
import com.example.movieapiapplication.presenter.MainPresenter;
import com.example.movieapiapplication.views.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainView {

    private Button btnSearch;

    private EditText etMovieName;

    private Button btnPreviousPage, btnNextPage;

    private TextView txtPageNumber;

    public int pageNumber = 1;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();

        MainPresenter mainPresenter = new MainPresenter(this);

        mainPresenter.getMovies(null, this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = etMovieName.getText().toString();
                if (movieName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Search box is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mainPresenter.getSearch(movieName, MainActivity.this);
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
                    mainPresenter.getSearch(movieName, MainActivity.this);
                    return true;
                }
                return false;
            }
        });


        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                mainPresenter.getMovies(pageNumber, MainActivity.this);
            }
        });

        btnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageNumber>1){
                    pageNumber--;
                    mainPresenter.getMovies(pageNumber, MainActivity.this);
                }
            }
        });

    }


    @Override
    public void getMovies(Main main) {
        List<ResultsItem> movies = main.getResults();
        String pageNumber = "Page: " + main.getPage();
        txtPageNumber.setText(pageNumber);
        MovieRecViewAdapter adapter = new MovieRecViewAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getSearch(Main main) {
        List<ResultsItem> movies = main.getResults();
        MovieRecViewAdapter adapter = new MovieRecViewAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }




    private void initViews() {
        recyclerView = findViewById(R.id.moviesRecView);

        btnSearch = findViewById(R.id.btnSearch);

        etMovieName = findViewById(R.id.etMovieName);

        btnNextPage = findViewById(R.id.btnNextPage);
        btnPreviousPage = findViewById(R.id.btnPreviousPage);

        txtPageNumber = findViewById(R.id.txtPageNumber);

    }




}