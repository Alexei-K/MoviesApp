package com.kolis.movies_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.movies_app.data.interfaces.RetrofitServices
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MovieRepositoryImpl : MovieRepository {

    //In parent app Firebase was use to store items of list+passwords, now stores password
    var db = FirebaseFirestore.getInstance()

    private val _allDresses = MutableLiveData<List<MovieModel>>()
    override fun getTrendingMovies(): LiveData<List<MovieModel>> {
        RetrofitClient.getClient().create(RetrofitServices::class.java).getMovieTrending().enqueue(
            object : Callback<ResponseModel> {
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.d("AlexLog", t.localizedMessage + "\n" + t.stackTrace)
                }

                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    _allDresses.postValue(response.body() as List<MovieModel>)

                }
            })
        return _allDresses
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