package com.kolis.movies_app.data

import androidx.lifecycle.LiveData
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver
import java.util.*

internal interface MovieRepository {
    fun addDress(model: MovieModel)
    fun getAllMovies(): LiveData<ArrayList<MovieModel>>
    fun addProfile(login: String, password: String)
    fun isRightPassword(login: String?, password: String?, observer: OnPasswordCheckObserver)
}