package com.example.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.DetailActivity;
import com.example.movies.R;
import com.example.movies.models.Film;

import org.parceler.Parcels;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder>  {
    Context context;
    List<Film> films;

    public FilmAdapter(Context context, List<Film> films) {
        this.context = context;
        this.films = films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View filmView = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        return new ViewHolder(filmView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film film = films.get(position);
        holder.bind(film);

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Film film) {
            tvTitle.setText(film.getTitle());
            tvOverview.setText(film.getOverview());
            String imageUrl;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = film.getBackdropPath();
            }
            else{
                imageUrl = film.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);

            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, film.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    // i.putExtra("title", film.getTitle());
                    i.putExtra("film", Parcels.wrap(film));
                    context.startActivity(i);
                }
            });
        }
    }
}
