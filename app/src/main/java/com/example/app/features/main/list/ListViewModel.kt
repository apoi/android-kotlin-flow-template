package com.example.app.features.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.data.pojo.Photo
import com.example.app.network.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: ListRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getPhotos(): LiveData<List<ListItem>> {
        return repository.getPhotos()
            .map { it.map { ListItem.from(it) } }
            .asLiveData(viewModelScope.coroutineContext)
    }
}
