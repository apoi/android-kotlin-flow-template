package com.example.app.domain.idlist.store

import com.example.app.data.room.Database
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import com.example.app.data.store.core.RoomDaoProxy
import com.example.app.data.store.core.RoomStoreCore
import com.example.app.domain.idlist.IdList

class IdListRoomCore(
    database: Database
) : RoomStoreCore<String, IdList, IdListEntity>(
    IdListEntity::toIdList,
    IdListEntity::fromIdList,
    IdListDaoProxy(database.idListDao(), StoreCore.takeNew())
)

private class IdListDaoProxy(
    private val dao: IdListDao,
    private val merger: Merger<IdListEntity>
) : RoomDaoProxy<String, IdListEntity>() {

    override suspend fun get(key: String) = dao.get(key)

    override suspend fun get(keys: List<String>) = dao.get(keys)

    override fun getStream(key: String) = dao.getStream(key)

    override suspend fun getAll() = dao.getAll()

    override fun getAllStream() = dao.getAllStream()

    override suspend fun put(key: String, value: IdListEntity) = dao.put(value, merger)

    override suspend fun put(items: Map<String, IdListEntity>) =
        dao.put(items.values.toList(), merger)

    override suspend fun delete(key: String) = dao.delete(key) > 0
}
