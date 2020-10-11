package com.example.app.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.photo.Photo
import com.example.app.model.photo.store.PhotoStore
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val id: Int,
    private val store: PhotoStore
) : ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo

    init {
        viewModelScope.launch {
            _photo.postValue(store.get(id))
        }
    }
}
