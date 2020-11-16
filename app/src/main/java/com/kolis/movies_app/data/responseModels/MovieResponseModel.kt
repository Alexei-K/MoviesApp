package com.kolis.movies_app.data.responseModels

import com.google.gson.annotations.SerializedName
import com.kolis.movies_app.data.dataModels.MovieModel

data class MovieResponseModel(
    val page: Int?, val total_results: Int?, val total_pages: Int?,
    @SerializedName("results") val movies: List<MovieModel>
)