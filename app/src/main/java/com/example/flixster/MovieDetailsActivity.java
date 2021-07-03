package com.example.flixster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {


    // the movie to display
    Movie movie;

    TextView mvTitle;
    TextView mvDescription;
    RatingBar rbVoteAverage;
    ImageView mvDetailsImg;
    Context context = this;

    String ytVideoKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details2);
        // unwrap the movie passed in via intent, using its simple name as a key
        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                        ACTION BAR
        ------------------------------------------------------------------------------------------------------------------------------------*/
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.popcorn_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        /* ------------------------------------------------------------------------------------------------------------------------------------*/

        mvTitle = findViewById(R.id.mvTitle);
        mvDescription = findViewById(R.id.mvDetailsDescription);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        mvDetailsImg = findViewById(R.id.mvDetailsImg);

        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        mvTitle.setText(movie.getTitle());
        mvDescription.setText(movie.getOverview());

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage/2.0f);
        rbVoteAverage.setIsIndicator(true);
        //-------------
        String imageUrl;
        imageUrl = movie.getBackdropPath();
        Glide.with(context).load(imageUrl).into(mvDetailsImg);



        AsyncHttpClient client = new AsyncHttpClient();
        String mv = movie.getVideoUrl() + getString(R.string.apiKey);
        String rv = movie.getReviewsUrl() + getString(R.string.apiKey);
        client.get( mv , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e("Trailer", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //Log.i(TAG, "Results" + results.toString() );
                    ytVideoKey = results.getJSONObject(0).getString("key");
                    Log.e("Trailer", ytVideoKey);
                    Log.e("Trailer", mv);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(ytVideoKey);
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("Trailer", "onSuccess");
            }

        });
        System.out.println(ytVideoKey);



        mvDetailsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> pack = new ArrayList<>();
                pack.add(ytVideoKey);
                pack.add(rv);
                //String ytKey = getYtKey();
                //System.out.println(ytKey);
                Intent intent = new Intent(context, MovieTrailerActivity.class);
                Log.e("Trailer", "wrapping ytvideo key" + ytVideoKey);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(pack));
                startActivity(intent);
            }
        });
    }


}