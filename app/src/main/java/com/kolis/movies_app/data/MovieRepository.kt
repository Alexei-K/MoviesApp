package com.kolis.movies_app.data

import androidx.lifecycle.LiveData
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver

internal interface MovieRepository {
    fun getTrendingMovies(): LiveData<List<MovieModel>>
    fun addProfile(login: String, password: String)
    fun isRightPassword(login: String?, password: String?, observer: OnPasswordCheckObserver)
}