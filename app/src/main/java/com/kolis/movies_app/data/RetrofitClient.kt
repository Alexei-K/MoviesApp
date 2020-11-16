package com.kolis.movies_app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private var retrofit: Retrofit? = null
    val BASE_URL: String = "https://api.themoviedb.org/3/"
    val TOKEN_MOVIES: String = "81bea2a4e95846ad434153851f6d24be"

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}