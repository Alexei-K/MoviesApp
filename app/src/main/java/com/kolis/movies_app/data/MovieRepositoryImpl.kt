package com.kolis.movies_app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.movies_app.data.MovieModel.Companion.fromFirebaseDocument
import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver
import java.util.*

class MovieRepositoryImpl : MovieRepository {

    //In parent app Firebase was use to store items of list+passwords, now stores password
    var db = FirebaseFirestore.getInstance()

    private val _allDresses = MutableLiveData<ArrayList<MovieModel>>()
    override fun getAllMovies(): LiveData<ArrayList<MovieModel>> {
        db.collection(DRESS_COLLECTION_PATH)
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val movieModels = ArrayList<MovieModel>()
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        movieModels.add(fromFirebaseDocument(document))
                    }
                    _allDresses.postValue(movieModels)
                } else {
                    Log.w(TAG, "Error getting data from firebase.", task.exception)
                }
            }
        return _allDresses
    }

    override fun addDress(model: MovieModel) {
        db.collection(DRESS_COLLECTION_PATH).add(model.toMap())
            .addOnSuccessListener { documentReference: DocumentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { documentReference: Exception? ->
                Log.d(
                    TAG,
                    "upload failed "
                )
            }
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