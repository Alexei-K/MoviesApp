package com.kolis.movies_app.data

import com.google.gson.annotations.SerializedName

data class ResponseModel(val page: Int?, val total_results: Int?, val total_pages: Int?, @SerializedName("results") val movies: List<MovieModel>)