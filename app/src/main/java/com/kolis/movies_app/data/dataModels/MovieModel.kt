package com.kolis.movies_app.data.dataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String,
    val vote_count: Int,
    var vote_average: Float
) : Parcelable