package com.example.app.features.main.list

import androidx.lifecycle.ViewModel
import com.example.app.data.state.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListViewModel(
    private val repository: ListRepository
) : ViewModel() {

    fun getPhotos(): Flow<State<List<ListItem>>> {
        return repository.getStream("")
            .map {
                when (it) {
                    State.Loading -> State.Loading
                    State.Empty -> State.Empty
                    is State.Success -> State.Success(it.value.map(ListItem.Companion::from))
                    is State.Error -> State.Error(it.error)
                }
            }
    }
}
