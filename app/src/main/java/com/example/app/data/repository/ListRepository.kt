package com.example.app.data.repository

import com.example.app.data.pojo.Photo
import com.example.app.network.PhotoApi
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListRepository(
    private val api: PhotoApi
) {

    fun getPhotos(): Flow<Result<List<Photo>>> {
        return flow {
            emit(getFromApi())
        }
    }

    private suspend fun getFromApi(): Result<List<Photo>> {
        return api.getPhotos()
    }

    private suspend fun getFromDatabase(): List<Photo> {
        return emptyList()
    }
}
