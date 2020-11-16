package com.kolis.movies_app.data


data class FullMovieModel(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val vote_count: Int,
    var vote_average: Float,
    val release_date: String,
    val genre_ids: List<Int>


)
