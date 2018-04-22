package com.jarivs.rentomojo.interfaces.get_movies_data;

import com.jarivs.rentomojo.models.MovieModel;

public interface IGetMoviesDataView {

    void onGetMoviesDataApiSuccess(int pid, MovieModel[] moviesList);

    void onGetMoviesDataApiError(int pid, String error);
}
