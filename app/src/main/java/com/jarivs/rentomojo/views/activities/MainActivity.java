package com.jarivs.rentomojo.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jarivs.rentomojo.R;
import com.jarivs.rentomojo.adapters.MoviesAdapter;
import com.jarivs.rentomojo.interfaces.get_movies_data.GetMoviesDataPresenterImpl;
import com.jarivs.rentomojo.interfaces.get_movies_data.IGetMoviesDataPresenter;
import com.jarivs.rentomojo.interfaces.get_movies_data.IGetMoviesDataView;
import com.jarivs.rentomojo.models.MovieModel;
import com.jarivs.rentomojo.utils.CommonItemSpaceDecoration;
import com.jarivs.rentomojo.utils.Sharedpreferences;
import com.jarivs.rentomojo.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IGetMoviesDataView {

    /**
     * References to views
     */
    @BindView(R.id.main_act_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movies_recycler_view)
    RecyclerView mMoviesRecyclerView;
    //MoviesCall Presenter reference
    private IGetMoviesDataPresenter mIGetMoviesDataPresenter;

    //Movies Adapter reference
    private MoviesAdapter mMoviesAdapter;

    private ArrayList<MovieModel> mMoviesList;


    /**
     * Firebase Reference and Firebase Database Object reference ,
     */
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private String dataStatus;
    private ArrayList<MovieModel> mLocalMovieList = new ArrayList<>();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 3; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLinearLayoutManager;

    private Sharedpreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeViews();

    }


    /**
     * place to initialization of views , adapters or presenters
     */
    private void initializeViews() {

        mPrefs = Sharedpreferences.getUserDataObj(MainActivity.this);
        mIGetMoviesDataPresenter = new GetMoviesDataPresenterImpl(MainActivity.this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("movies_data");


        DatabaseReference people = FirebaseDatabase.getInstance().getReference().child("movies_data");
        people.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataStatus = "isThere";
                } else {
                    dataStatus = "isNotThere";
                    mIGetMoviesDataPresenter.getMoviesDataApi();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Value ==", "is not there");

            }
        });

    }


    /**
     * Success Callback of movies api
     */
    @Override
    public void onGetMoviesDataApiSuccess(int pid, MovieModel[] moviesList) {
        Utils.stopProgress(MainActivity.this);
        Log.d("COming ", "here3");

        mMoviesList = new ArrayList<MovieModel>(Arrays.asList(moviesList));


        createMovie();
        mPrefs.setDataSavedStatus("Saved");
    }


    /**
     * error callback of movies api
     */
    @Override
    public void onGetMoviesDataApiError(int pid, String error) {
        Utils.stopProgress(MainActivity.this);
        Utils.showToast(MainActivity.this, error);
    }


    private void createMovie() {

        try {


            for (int i = 0; i < mMoviesList.size(); i++) {
                userId = mFirebaseDatabase.push().getKey();
                MovieModel moviesList = new MovieModel(mMoviesList.get(i).getTitle(), mMoviesList.get(i).getImage(),
                        mMoviesList.get(i).getRating(), mMoviesList.get(i).getReleaseYear());
                mFirebaseDatabase.child(userId).setValue(moviesList);
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        mMoviesList = new ArrayList<>();


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous post list
                mMoviesList.clear();
                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                    MovieModel movieList = myDataSnapshot.getValue(MovieModel.class);
                    mMoviesList.add(movieList);
                }

                mLocalMovieList.addAll(mMoviesList);
                settingMoviesRecyclerView(mLocalMovieList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mMoviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    onLoadMore();

                    loading = true;
                }
            }
        });


    }

    private void onLoadMore() {
        try {
            for (int i = 0; i < 3; i++) {
                mLocalMovieList.add(mLocalMovieList.get(i));
            }
            mMoviesAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * setting movies in a recycler view
     *
     * @param mLocalMovieList
     */
    private void settingMoviesRecyclerView(final ArrayList<MovieModel> mLocalMovieList) {

        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mMoviesRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMoviesRecyclerView.setItemViewCacheSize(15);
        mMoviesRecyclerView.setDrawingCacheEnabled(true);
        mMoviesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mMoviesAdapter = new MoviesAdapter(MainActivity.this, mLocalMovieList);
        mMoviesRecyclerView.addItemDecoration(new CommonItemSpaceDecoration(5, false));
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
        mMoviesAdapter.notifyDataSetChanged();


    }
}




