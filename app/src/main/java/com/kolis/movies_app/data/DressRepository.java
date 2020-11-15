package com.kolis.movies_app.data;

import androidx.lifecycle.LiveData;

import com.kolis.movies_app.ui.start_info.OnPasswordCheckObserver;

import java.util.ArrayList;

interface DressRepository {

    void addDress(DressModel model);

    LiveData<ArrayList<DressModel>> getAllDressesLD();

    void addProfile(String login, String password);

    void isRightPassword(String login, String password, OnPasswordCheckObserver observer);

}
