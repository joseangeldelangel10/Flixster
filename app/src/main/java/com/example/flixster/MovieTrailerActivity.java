package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.adapters.ReviewsAdapter;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter;
import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    //Movie movie;
    String ytVideoKey;
    String rvUrl;
    ArrayList<String> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //movie = Parcels.unwrap(this.getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        ArrayList<String> pack = Parcels.unwrap(this.getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        ytVideoKey = pack.get(0);
        rvUrl = pack.get(1);


        // temporary test video id
        final String videoId = "tKodtNFpzBA";

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize( "AIzaSyCps4SAXssA8iJY4Fp5q7dZcuLEJ66W4M4" , new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                //youTubePlayer.cueVideo(videoId);
                Log.e("Trailer", "loading video with ytVkey" + ytVideoKey);
                Log.e("Trailer", rvUrl);
                youTubePlayer.cueVideo(ytVideoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });



        /* We reference our recycled view widget and create the objects needed to fill such RV*/
        RecyclerView rvReviews = findViewById(R.id.rvReviews);
        reviews = new ArrayList<>();
        //create the adapter
        final ReviewsAdapter rvAdapter = new ReviewsAdapter(this, reviews);
        //set the adapter on the recycler view
        rvReviews.setAdapter(rvAdapter);
        //set a layout on the RV
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get( rvUrl , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e("Trailer", "Succesfully conected to reviews db");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //Log.i(TAG, "Results" + results.toString() );
                    //reviews.add(results.getJSONObject(0).getString("content"));
                    for (int j = 0; j < results.length(); j++){
                        reviews.add( results.getJSONObject(j).getString("content") );
                    }
                    Log.e("Trailer", "succesfully fetched reviews");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("Trailer", "onSuccess");
            }

        });

        /*for(Integer i = 0; i < 4; i++){
            reviews.add(i.toString());
        }*/
        rvAdapter.notifyDataSetChanged();


    }
}