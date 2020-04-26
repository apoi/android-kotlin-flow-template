package com.example.app.features.home.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.ListRepository
import com.example.app.network.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ListViewModel(
    private val repository: ListRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val data = MutableLiveData<List<PhotoItem>>()
    private var fetchJob: Job? = null

    fun getPhotos(): LiveData<List<PhotoItem>> = data

    fun fetchPhotos() {
        fetchJob?.apply { if (isActive) cancel() }
        fetchJob = viewModelScope.launch(dispatcher) {
            repository.getPhotos()
                .collect {
                    when (it) {
                        is Result.Success -> {
                            val items = it.value?.map { PhotoItem.from(it) }
                            data.postValue(items)
                        }
                        else -> Timber.e(it.toString())
                    }
                }
        }
    }
}
