package com.kolis.test_catalog_app.ui.start_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartInfoViewModel extends ViewModel {

    private MutableLiveData<Integer> _image = new MutableLiveData<>();

    public LiveData<Integer> getImage() {
        return _image;
    }

    private MutableLiveData<String> _title = new MutableLiveData<>();

    public LiveData<String> getTitle() {
        return _title;
    }

    private MutableLiveData<String> _text = new MutableLiveData<>();

    public LiveData<String> getText() {
        return _text;
    }

    public void setUpInfo(int imageSource, String title, String text) {
        _image.postValue(imageSource);
        _title.postValue(title);
        _text.postValue(text);
    }


}
