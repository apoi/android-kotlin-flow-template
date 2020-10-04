package com.example.app.features.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.pojo.Photo
import com.example.app.features.main.details.store.DetailsStore
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val id: Int,
    private val store: DetailsStore
) : ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo

    init {
        viewModelScope.launch {
            _photo.postValue(store.get(id))
        }
    }
}
