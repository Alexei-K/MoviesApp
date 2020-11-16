package com.kolis.movies_app.data.interfaces

import com.kolis.movies_app.data.ResponseModel
import com.kolis.movies_app.data.RetrofitClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @GET("trending/movie/week")
    fun getMovieTrending(@Query("api_key") api_key: String = RetrofitClient.TOKEN_MOVIES): Call<ResponseModel>

    @GET("/movie/{id}")
    fun getMovieInfo(@Query("api_key") api_key: String = RetrofitClient.TOKEN_MOVIES, @Path("id") id: Int): Call<ResponseModel>
}