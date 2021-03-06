package com.example.app.domain.photo.store

import com.example.app.data.room.Database
import com.example.app.data.store.Merger
import com.example.app.data.store.core.RoomDaoProxy
import com.example.app.data.store.core.RoomStoreCore
import com.example.app.domain.photo.Photo

class PhotoRoomCore(
    database: Database
) : RoomStoreCore<Int, Photo, PhotoEntity>(
    PhotoEntity::toPhoto,
    PhotoEntity::fromPhoto,
    PhotoDaoProxy(database.photoDao(), PhotoEntity.merger)
)

private class PhotoDaoProxy(
    private val dao: PhotoDao,
    private val merger: Merger<PhotoEntity>
) : RoomDaoProxy<Int, PhotoEntity>() {

    override suspend fun get(key: Int) = dao.get(key)

    override suspend fun get(keys: List<Int>) = dao.get(keys)

    override fun getStream(key: Int) = dao.getStream(key)

    override suspend fun getAll() = dao.getAll()

    override fun getAllStream() = dao.getAllStream()

    override suspend fun put(key: Int, value: PhotoEntity) = dao.put(value, merger)

    override suspend fun put(items: Map<Int, PhotoEntity>) = dao.put(items.values.toList(), merger)

    override suspend fun delete(key: Int) = dao.delete(key) > 0
}
