package com.kolis.movies_app.util

import android.widget.ImageView
import com.kolis.movies_app.R
import com.squareup.picasso.Picasso

class PhotoUploader {

    /**
     * Uploads photos into ImageView by photo path(format: "/xrI4EnZWftpo1B7tTvlMUXVOikd.jpg")
     */
    companion object {
        fun uploadPhotoFromMoviesDb(path: String, iv: ImageView) {
            Picasso.get()
                .load(AppConstants.IMG_UPLOAD_BASE_URL + path)
                .placeholder(R.drawable.loading)
                .error(R.drawable.image_not_found)
                .into(iv)

        }
    }
}