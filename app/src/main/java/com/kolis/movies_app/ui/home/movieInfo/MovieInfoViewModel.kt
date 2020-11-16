package com.kolis.movies_app.ui.home.movieInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kolis.movies_app.data.MovieRepositoryImpl
import com.kolis.movies_app.data.dataModels.ExtraMovieModel

class MovieInfoViewModel : ViewModel() {
    var db = MovieRepositoryImpl()

    //    private val _fullModel = MutableLiveData<FullMovieModel>()
//    val fullModel: LiveData<FullMovieModel> = _fullModel
//
    fun getExtraMovieInfo(id: Int): LiveData<ExtraMovieModel> {
        return db.getExtraMovieInfo(id)
    }
}