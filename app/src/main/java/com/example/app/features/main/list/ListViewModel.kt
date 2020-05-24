package com.example.app.features.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.data.state.State
import kotlinx.coroutines.flow.map

class ListViewModel(
    private val repository: ListRepository
) : ViewModel() {

    fun getPhotos(): LiveData<State<List<ListItem>>> {
        return repository.getStream("")
            .map {
                when (it) {
                    State.Loading -> State.Loading
                    State.Empty -> State.Empty
                    is State.Success -> State.Success(it.value.map(ListItem.Companion::from))
                    is State.Error -> State.Error(it.error)
                }
            }
            .asLiveData(viewModelScope.coroutineContext)
    }
}
