package com.kolis.movies_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.movies_app.data.dataModels.ExtraMovieModel
import com.kolis.movies_app.data.dataModels.MovieModel
import com.kolis.movies_app.data.interfaces.RetrofitServices
import com.kolis.movies_app.data.responseModels.MovieResponseModel
import com.kolis.movies_app.data.responseModels.PeopleResponseModel
import com.kolis.movies_app.data.responseModels.ReleaseAndGenresResponseModel
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieRepositoryImpl : MovieRepository {

    //In parent app Firebase was use to store items of list+passwords, now stores password
    var db = FirebaseFirestore.getInstance()
    val retrofitClient = RetrofitClient.getClient().create(RetrofitServices::class.java)
    override fun getTrendingMovies(page: Int): LiveData<List<MovieModel>> {
         val _trendingMovies = MutableLiveData<List<MovieModel>>()

        retrofitClient.getMovieTrending(page = page).enqueue(
            object : Callback<MovieResponseModel> {
                override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                    Log.d("AlexLog", t.localizedMessage + "\n" + t.stackTrace)
                }

                override fun onResponse(call: Call<MovieResponseModel>, movieResponse: Response<MovieResponseModel>) {
                    val movies = (movieResponse.body() as MovieResponseModel).movies.filter { it.title != "" && it.title != null }
                    movies.forEach { it.vote_average /= 2 }
                    _trendingMovies.postValue(movies)

                }
            })
        return _trendingMovies
    }

    override fun searchMovie(name: String): LiveData<List<MovieModel>> {
        val _searchedMovies = MutableLiveData<List<MovieModel>>()

        retrofitClient.getMovieSearch(name=name).enqueue(
            object : Callback<MovieResponseModel> {
                override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                    Log.d("AlexLog", t.localizedMessage + "\n" + t.stackTrace)
                }

                override fun onResponse(call: Call<MovieResponseModel>, movieResponse: Response<MovieResponseModel>) {
                    val movies = (movieResponse.body() as MovieResponseModel).movies.filter { it.title != "" && it.title != null }
                    movies.forEach { it.vote_average /= 2 }
                    _searchedMovies.postValue(movies)

                }
            })
        return _searchedMovies    }

    override fun getExtraMovieInfo(id: Int): LiveData<ExtraMovieModel> {
        val _extraInfoVM = MutableLiveData<ExtraMovieModel>().apply { value = ExtraMovieModel("", listOf(), listOf(), listOf()) }
        retrofitClient.getMovieInfo(id = id).enqueue(object : Callback<ReleaseAndGenresResponseModel> {
            override fun onFailure(call: Call<ReleaseAndGenresResponseModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReleaseAndGenresResponseModel>, movieResponse: Response<ReleaseAndGenresResponseModel>) {
                if (movieResponse.isSuccessful) {
                    val response = movieResponse.body() as ReleaseAndGenresResponseModel
                    _extraInfoVM.postValue(_extraInfoVM.value?.copy(release_date = response.release_date!!, genres = response.genres!!))
                }
            }
        })
        retrofitClient.getMoviePeople(id = id).enqueue(object : Callback<PeopleResponseModel> {
            override fun onFailure(call: Call<PeopleResponseModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<PeopleResponseModel>, movieResponse: Response<PeopleResponseModel>) {
                if (movieResponse.isSuccessful) {
                    val response = movieResponse.body() as PeopleResponseModel
                    _extraInfoVM.postValue(_extraInfoVM.value?.copy(casts = response.cast!!, crew = response.crew!!))
                }
            }
        })
        return _extraInfoVM
    }


    override fun addProfile(login: String, password: String) {
        val profile = HashMap<String, String>()
        profile["login"] = login
        profile["password"] = password
        val date = Date(System.currentTimeMillis())
        profile["register date"] = date.toString()
        db.collection(PROFILES_COLLECTION_PATH)
            .add(profile)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(
                    TAG,
                    "Login added " + documentReference.id
                )
            }
            .addOnFailureListener { documentReference: Exception? ->
                Log.d(
                    TAG,
                    "Login adding failed! "
                )
            }
    }

    override fun isRightPassword(login: String?, password: String?, observer: OnPasswordCheckObserver) {
        db.collection(PROFILES_COLLECTION_PATH).whereEqualTo("login", login).whereEqualTo("password", password).get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        if (!task.result!!.isEmpty) {
                            observer.onPasswordCorrect(login!!, password!!)
                        } else {
                            observer.onPasswordWrong()
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting data from firebase.", task.exception)
                }
            }
    }

    companion object {
        private const val DRESS_COLLECTION_PATH = "dresses"
        private const val PROFILES_COLLECTION_PATH = "profiles_4578"
        var TAG = "firebase_debug"
    }
}