package com.example.app.model.idlist.store

import com.example.app.data.room.Database
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import com.example.app.data.store.core.RoomDaoProxy
import com.example.app.data.store.core.RoomStoreCore
import com.example.app.model.idlist.IdList

class IdListRoomCore(
    database: Database
) : RoomStoreCore<String, IdList, IdListEntity>(
    IdListEntity::toIdList,
    IdListEntity::fromIdList,
    IdListProxy(database.idListDao(), StoreCore.takeNew())
)

private class IdListProxy(
    private val dao: IdListDao,
    private val merger: Merger<IdListEntity>
) : RoomDaoProxy<String, IdList, IdListEntity> {

    override suspend fun get(key: String) = dao.get(key)

    override suspend fun get(keys: List<String>) = dao.get(keys)

    override fun getStream(key: String) = dao.getStream(key)

    override suspend fun getAll() = dao.getAll()

    override fun getAllStream() = dao.getAllStream()

    override suspend fun put(value: IdListEntity) = dao.put(value, merger)

    override suspend fun put(values: List<IdListEntity>) = dao.put(values, merger)

    override suspend fun delete(key: String) = dao.delete(key)
}
