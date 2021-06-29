package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    Context context;
    List<String> reviews;

    public ReviewsAdapter(Context context, List<String> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        textView.setBackground( new ColorDrawable(0x95E1A901) );
        textView.setTextColor( Color.parseColor("#000000"));
        textView.setTextSize(15);
        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String rv = reviews.get(position);
        Integer pos = new Integer(position);
        pos += 1;
        String final_rv = "\nReview " +  pos.toString() + ": \n\n" + rv + "\n\n\n\n" + "___________________________________________________________";
        holder.review.setText(final_rv);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView review;

        public ViewHolder(@NonNull TextView itemView) {
            //ivPoster = itemView.findViewById(R.id.ivPoster);
            super(itemView);
            review = itemView;
        }
    }
}
