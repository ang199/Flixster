package com.example.movies.models;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Film {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    int filmID;

    public Film(){
        // needed by parceler
    }

    public Film(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        filmID = jsonObject.getInt("id");
    }
    public static List<Film> fromJsonArray(JSONArray filmJsonArray) throws JSONException {
        List<Film> films = new ArrayList<>();
        for (int i = 0; i < filmJsonArray.length(); i++){
            films.add(new Film(filmJsonArray.getJSONObject(i)));
        }
        return films;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getFilmID() {
        return filmID;
    }
}
