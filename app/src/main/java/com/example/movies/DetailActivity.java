package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movies.models.Film;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyAKqQ4IGGqWp7vNCSLC6cxSeoge3OHfd8w";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvT;
    TextView tvOV;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvT = findViewById(R.id.tvT);
        tvOV = findViewById(R.id.tvOV);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);

        // String title = getIntent().getStringExtra("title");
        Film film = Parcels.unwrap(getIntent().getParcelableExtra("film"));
        tvT.setText(film.getTitle());
        tvOV.setText(film.getOverview());
        ratingBar.setRating((float)film.getRating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, film.getFilmID()), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        try {
                            JSONArray results = json.jsonObject.getJSONArray("results");
                            if (results.length() == 0) {
                                return;
                            }
                            String youtubeKey = results.getJSONObject(0).getString("key");
                            Log.d("DetailActivity", youtubeKey);
                            initializeYoutube(youtubeKey);
                        } catch (JSONException e){
                            Log.d("DetailActivity", "Failed");
                        }
                        }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                    }
                });

    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "Success");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "Failure");

            }
        });
    }
}