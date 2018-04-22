package com.jarivs.rentomojo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarivs.rentomojo.R;
import com.jarivs.rentomojo.models.MovieModel;
import com.jarivs.rentomojo.viewholders.MoviesViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private Context mContext;
    private ArrayList<MovieModel> mMoviesList;


    public MoviesAdapter(Context context, ArrayList<MovieModel> mMoviesList) {
        this.mContext = context;
        this.mMoviesList = mMoviesList;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_movies_row, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, final int position) {

        try {

            if (mMoviesList.get(position).getTitle() != null) {
                holder.mMovieNameTextView.setText(mMoviesList.get(position).getTitle().toString());
            }

            if (mMoviesList.get(position).getRating() != null) {
                holder.mMovieRatingTextView.setText("Rating: " + mMoviesList.get(position).getRating().toString());
            }

            if (mMoviesList.get(position).getReleaseYear() != null) {
                holder.mMovieReleaseTextView.setText("Release year: " + mMoviesList.get(position).getReleaseYear().toString());
            }

            if (mMoviesList.get(position).getImage() != null) {
                Picasso.get().load(mMoviesList.get(position).getImage()).resize(100, 100).centerCrop().
                        networkPolicy(NetworkPolicy.OFFLINE).into(holder.mMovieParentImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(mMoviesList.get(position).getImage()).resize(100, 100).centerCrop().
                                into(holder.mMovieParentImageView);
                    }
                });


                holder.mMovieParentCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.mMovieRatingReleaseLinearLayout.getVisibility() == View.GONE) {
                            holder.mMovieRatingReleaseLinearLayout.setVisibility(View.VISIBLE);
                        } else if (holder.mMovieRatingReleaseLinearLayout.getVisibility() == View.VISIBLE) {
                            holder.mMovieRatingReleaseLinearLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }

            holder.mItemMoviePositionTextView.setText(String.valueOf(position+1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}