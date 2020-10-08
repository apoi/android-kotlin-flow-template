package com.example.app.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.model.Photo
import com.example.app.feature.details.store.DetailsStore
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
