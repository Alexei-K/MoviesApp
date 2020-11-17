package com.kolis.movies_app.data

import androidx.lifecycle.LiveData
import com.kolis.movies_app.data.dataModels.ExtraMovieModel
import com.kolis.movies_app.data.dataModels.MovieModel
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver

internal interface MovieRepository {
    fun getTrendingMovies(page: Int): LiveData<List<MovieModel>>
    fun searchMovie(name:String): LiveData<List<MovieModel>>
    fun getExtraMovieInfo(id: Int): LiveData<ExtraMovieModel>
    fun addProfile(login: String, password: String)
    fun isRightPassword(login: String?, password: String?, observer: OnPasswordCheckObserver)
}