package com.example.app.features.main.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.pojo.Photo
import com.example.app.data.state.State
import com.example.app.features.main.album.adapter.AlbumItemModel
import com.example.app.features.main.album.store.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _photos = MutableLiveData<State<List<AlbumItemModel>>>()
    val photos: LiveData<State<List<AlbumItemModel>>> = _photos

    init {
        viewModelScope.launch {
            getPhotos()
                .collect { _photos.postValue(it) }
        }
    }

    private fun getPhotos(): Flow<State<List<AlbumItemModel>>> {
        return repository.getStream("")
            .map {
                when (it) {
                    State.Loading -> State.Loading
                    State.Empty -> State.Empty
                    is State.Success -> State.Success(it.value.map((AlbumItemModel)::from))
                    is State.Error -> State.Error(it.error)
                }
            }
    }
}
