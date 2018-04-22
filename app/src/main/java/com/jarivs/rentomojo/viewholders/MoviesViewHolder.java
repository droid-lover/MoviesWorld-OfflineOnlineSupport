package com.jarivs.rentomojo.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarivs.rentomojo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_movie_iv)
    public ImageView mMovieParentImageView;

    @BindView(R.id.item_movie_name_tv)
    public TextView mMovieNameTextView;

    @BindView(R.id.item_movie_rating_tv)
    public TextView mMovieRatingTextView;

    @BindView(R.id.item_movie_release_year_tv)
    public TextView mMovieReleaseTextView;

    @BindView(R.id.item_movie_rating_relase_details_ll)
    public LinearLayout mMovieRatingReleaseLinearLayout;

    @BindView(R.id.item_movie_parent_card_view)
    public CardView mMovieParentCardView;

    @BindView(R.id.item_movie_position_tv)
    public TextView mItemMoviePositionTextView;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
