package com.kolis.movies_app.ui.start_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartInfoViewModel : ViewModel() {
    private val _image = MutableLiveData<Int?>()
    val image: LiveData<Int?>
        get() = _image

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?>
        get() = _title

    private val _text = MutableLiveData<String?>()
    val text: LiveData<String?>
        get() = _text

    fun setUpInfo(imageSource: Int, title: String?, text: String?) {
        _image.postValue(imageSource)
        _title.postValue(title)
        _text.postValue(text)
    }
}