package com.example.app.model.photo.store

import com.example.app.data.room.Database
import com.example.app.data.store.StoreCore
import com.example.app.model.photo.Photo
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PhotoRoomCore(
    database: Database
) : StoreCore<Int, Photo> {

    private val dao = database.photoDao()

    private val insertStream = ConflatedBroadcastChannel<Photo>()

    override suspend fun get(key: Int): Photo? {
        return dao.get(key)?.toPhoto()
    }

    override fun getStream(key: Int): Flow<Photo> {
        return dao.getStream(key)
            .distinctUntilChanged()
            .map(PhotoEntity::toPhoto)
    }

    override fun getInsertStream(): Flow<Photo> {
        return insertStream.asFlow()
    }

    override suspend fun getAll(): List<Photo> {
        return dao.getAll()
            .map(PhotoEntity::toPhoto)
    }

    override fun getAllStream(): Flow<List<Photo>> {
        return dao.getAllStream()
            .map { it.map(PhotoEntity::toPhoto) }
    }

    override suspend fun put(key: Int, value: Photo): Boolean {
        val updated = dao.put(PhotoEntity.fromPhoto(value), StoreCore.takeNew())
        if (updated) insertStream.send(value)
        return updated
    }

    override suspend fun put(items: Map<Int, Photo>): Boolean {
        val updated = dao.put(items.values.map(PhotoEntity::fromPhoto), StoreCore.takeNew())
        updated.map(PhotoEntity::toPhoto).forEach { insertStream.send(it) }
        return updated.isNotEmpty()
    }

    override suspend fun delete(key: Int): Boolean {
        return dao.delete(key) > 0
    }
}
