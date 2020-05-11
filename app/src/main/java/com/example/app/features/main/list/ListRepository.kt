package com.example.app.features.main.list

import com.example.app.data.pojo.Photo
import com.example.app.data.repository.Repository
import com.example.app.network.PhotoApi
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow

class ListRepository(
    private val store: ListStore,
    private val api: PhotoApi
) : Repository<List<Photo>>() {

    override suspend fun persist(response: List<Photo>) {
        store.put(response)
    }

    override suspend fun getLocal(): List<Photo> {
        return store.get()
    }

    override fun getLocalStream(): Flow<List<Photo>> {
        return store.getStream()
    }

    override suspend fun fetchRemote(): Result<List<Photo>> {
        return api.getPhotos()
    }
}
