package com.example.app.features.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.data.pojo.Photo

class DetailsViewModel(
    private val id: Int
) : ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo
}
