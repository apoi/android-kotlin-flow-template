package com.example.app.feature.album.lazy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.state.State
import com.example.app.domain.photo.repository.PhotoListRepository
import com.example.app.domain.photo.store.PhotoStore
import com.example.app.feature.album.lazy.adapter.LazyAlbumItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LazyAlbumViewModel(
    private val repository: PhotoListRepository,
    private val photoStore: PhotoStore
) : ViewModel() {

    private val _photos = MutableLiveData<State<List<LazyAlbumItemModel>>>()
    val photos: LiveData<State<List<LazyAlbumItemModel>>> = _photos

    init {
        viewModelScope.launch {
            getPhotos()
                .collect { _photos.postValue(it) }
        }
    }

    private fun getPhotos(): Flow<State<List<LazyAlbumItemModel>>> {
        return repository.getStream()
            .map {
                when (it) {
                    State.Loading -> State.Loading
                    State.Empty -> State.Empty
                    is State.Success -> {
                        State.Success(
                            it.value.map { (id) ->
                                LazyAlbumItemModel(id, photoStore)
                            }
                        )
                    }
                    is State.Error -> State.Error(it.error)
                }
            }
    }
}
