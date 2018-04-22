package com.jarivs.rentomojo.interfaces.get_movies_data;

import com.google.gson.Gson;
import com.jarivs.rentomojo.models.MovieModel;
import com.jarivs.rentomojo.networking.AsyncInteractor;
import com.jarivs.rentomojo.networking.OnRequestListener;
import com.jarivs.rentomojo.utils.AppConstants;
import com.jarivs.rentomojo.utils.NetworkStatus;
import com.jarivs.rentomojo.utils.Utils;
import com.jarivs.rentomojo.views.activities.MainActivity;

import org.json.JSONException;

public class GetMoviesDataPresenterImpl implements IGetMoviesDataPresenter, OnRequestListener {


    private IGetMoviesDataView mIGetMoviesDataView;
    private MainActivity mMainActivity;
    private AsyncInteractor mAsyncInteractor;
    private MovieModel[] mMoviesList;

    public GetMoviesDataPresenterImpl(IGetMoviesDataView mIGetMoviesDataView) {
        this.mIGetMoviesDataView = mIGetMoviesDataView;
        this.mMainActivity = (MainActivity) mIGetMoviesDataView;
        mAsyncInteractor = new AsyncInteractor();
    }


    @Override
    public void getMoviesDataApi() {
        if (NetworkStatus.checkNetworkStatus(mMainActivity)) {
            Utils.showProgress(mMainActivity);

            mAsyncInteractor.validateCredentialsAsync(this, AppConstants.TAG_ID_GET_MOVIES_DATA,
                    AppConstants.URL.GET_MOVIES_DATA.getUrl());
        } else {
            Utils.showToast(mMainActivity, "Please connect to Internet");
        }
    }

    @Override
    public void onRequestCompletion(int pid, String responseJson) throws JSONException {
        if (pid == AppConstants.TAG_ID_GET_MOVIES_DATA) {

            if (responseJson != null) {

                Gson gson = new Gson();

                mMoviesList = gson.fromJson(responseJson, MovieModel[].class);
                mIGetMoviesDataView.onGetMoviesDataApiSuccess(pid, mMoviesList);

            } else {
                mIGetMoviesDataView.onGetMoviesDataApiError(pid, "Some Error Occured , Response from server is null!");
            }
        }
    }

    @Override
    public void onRequestCompletionError(int pid, String error) {
        if (pid == AppConstants.TAG_ID_GET_MOVIES_DATA) {
            mIGetMoviesDataView.onGetMoviesDataApiError(pid, error);
        }
    }

}
