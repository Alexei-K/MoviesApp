package com.kolis.movies_app.data

import android.os.Parcelable
import com.kolis.movies_app.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val vote_count: Int,
    val vote_average: Float
) : Parcelable {


    // For testing purpose
    fun getTestImageResource(): Int {
        return when (id % 4) {
            0 -> R.drawable.image_test
            1 -> R.drawable.image_test2
            2 -> R.drawable.image_test3
            3 -> R.drawable.image_test4
            else -> R.drawable.image_test4
        }

    }

}