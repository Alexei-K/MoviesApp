package com.kolis.movies_app.data.interfaces

import com.kolis.movies_app.data.RetrofitClient
import com.kolis.movies_app.data.responseModels.MovieResponseModel
import com.kolis.movies_app.data.responseModels.PeopleResponseModel
import com.kolis.movies_app.data.responseModels.ReleaseAndGenresResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @GET("trending/movie/week")
    fun getMovieTrending(@Query("api_key") api_key: String = RetrofitClient.TOKEN_MOVIES, @Query("page") page: Int = 1): Call<MovieResponseModel>

    @GET("movie/{id}")
    fun getMovieInfo(
        @Path("id") id: Int, @Query("api_key") api_key: String = RetrofitClient.TOKEN_MOVIES,
        @Query("language") language: String = "en-US"
    ): Call<ReleaseAndGenresResponseModel>

    @GET("movie/{movie_id}/credits")
    fun getMoviePeople(@Path("movie_id") id: Int, @Query("api_key") api_key: String = RetrofitClient.TOKEN_MOVIES): Call<PeopleResponseModel>
}