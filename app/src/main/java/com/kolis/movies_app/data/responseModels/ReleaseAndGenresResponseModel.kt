package com.kolis.movies_app.data.responseModels

import com.kolis.movies_app.data.dataModels.GenreModel

data class ReleaseAndGenresResponseModel(
    val release_date: String?,
    val genres: List<GenreModel>?
)