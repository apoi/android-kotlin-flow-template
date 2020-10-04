package com.example.app.features.main.album

import androidx.lifecycle.ViewModel
import com.example.app.data.state.State
import com.example.app.features.main.album.adapter.AlbumItemModel
import com.example.app.features.main.album.store.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumViewModel(
    private val repository: AlbumRepository
) : ViewModel() {

    fun getPhotos(): Flow<State<List<AlbumItemModel>>> {
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
