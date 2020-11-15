package com.kolis.test_catalog_app.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.kolis.test_catalog_app.ui.start_info.OnPasswordCheckObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DressRepositoryImpl implements DressRepository {
    private static String DRESS_COLLECTION_PATH = "dresses";
    private static String PROFILES_COLLECTION_PATH = "profiles_4578";
    public static String TAG = "firebase_debug";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<ArrayList<DressModel>> _allDresses = new MutableLiveData<>();

    private LiveData<ArrayList<DressModel>> allDresses() {
        return _allDresses;
    }

    @Override
    public void addDress(DressModel model) {
        db.collection(DRESS_COLLECTION_PATH).add(model.toMap())
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(documentReference -> Log.d(TAG, "upload failed "));
    }

    @Override
    public LiveData<ArrayList<DressModel>> getAllDressesLD() {
        db.collection(DRESS_COLLECTION_PATH)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<DressModel> dressModels = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            dressModels.add(DressModel.Companion.fromFirebaseDocument(document));
                        }
                        _allDresses.postValue(dressModels);
                    } else {
                        Log.w(TAG, "Error getting data from firebase.", task.getException());
                    }
                });
        return allDresses();
    }

    @Override
    public void addProfile(String login, String password) {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("login", login);
        profile.put("password", password);
        Date date = new Date(System.currentTimeMillis());
        profile.put("register date", date.toString());

        db.collection(PROFILES_COLLECTION_PATH)
                .add(profile)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Login added " + documentReference.getId()))
                .addOnFailureListener(documentReference -> Log.d(TAG, "Login adding failed! "));
    }

    @Override
    public void isRightPassword(String login, String password, OnPasswordCheckObserver observer) {
        db.collection(PROFILES_COLLECTION_PATH).whereEqualTo("login", login).whereEqualTo("password", password).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            if ((!task.getResult().isEmpty())) {
                                observer.onPasswordCorrect(login, password);
                            } else {
                                observer.onPasswordWrong();
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting data from firebase.", task.getException());
                    }
                });
    }
}
