package com.example.movieapiapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapiapplication.R;
import com.example.movieapiapplication.models.ResultsItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieRecViewAdapter extends RecyclerView.Adapter<MovieRecViewAdapter.ViewHolder>{

    private static final String IMG_PATH = "https://image.tmdb.org/t/p/w1280";

    private List<ResultsItem> movies =new ArrayList<>();

    private Context mContext;

    public MovieRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieRecViewAdapter.ViewHolder holder, int position) {
        ResultsItem movie = movies.get(position);

        String imgUrl = IMG_PATH + movie.getPosterPath();

        Glide.with(mContext)
                .asBitmap()
                .load(imgUrl)
                .into(holder.imgMovieIcon);

        holder.txtMovieName.setText(movie.getTitle());
        holder.txtOriginalLan.setText(movie.getOriginalLanguage());
        holder.txtReleaseDate.setText(movie.getReleaseDate());
        holder.txtAverageGrade.setText(String.valueOf(movie.getVoteAverage()));
        holder.txtNumberOfVotes.setText(String.valueOf(movie.getVoteCount()));

        holder.btnArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnArrowDown.setVisibility(View.GONE);
                holder.expandedRelLay.setVisibility(View.VISIBLE);
            }
        });
        holder.btnArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnArrowDown.setVisibility(View.VISIBLE);
                holder.expandedRelLay.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<ResultsItem> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtMovieName, txtOriginalLan, txtReleaseDate, txtAverageGrade, txtNumberOfVotes;
        private ImageView imgMovieIcon;

        private ImageView btnArrowUp, btnArrowDown;

        private RelativeLayout expandedRelLay;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtOriginalLan = itemView.findViewById(R.id.txtOriginalLan);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtAverageGrade = itemView.findViewById(R.id.txtAverageGrade);
            txtNumberOfVotes = itemView.findViewById(R.id.txtNumberOfVotes);

            imgMovieIcon = itemView.findViewById(R.id.imgMovieIcon);

            expandedRelLay = itemView.findViewById(R.id.expandedRelLayout);

            btnArrowUp = itemView.findViewById(R.id.btnArrowUp);
            btnArrowDown = itemView.findViewById(R.id.btnArrowDown);
        }
    }
}
