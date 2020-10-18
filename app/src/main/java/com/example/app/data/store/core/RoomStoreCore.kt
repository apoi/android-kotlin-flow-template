package com.example.app.data.store.core

import com.example.app.data.store.StoreCore
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Shared implementation for StoreCores with Room as the backing store.
 */
abstract class RoomStoreCore<K, V, E>(
    private val fromEntity: (E) -> V,
    private val toEntity: (V) -> E,
    private val dao: RoomDaoProxy<K, E>
) : StoreCore<K, V> {

    private val insertStream = ConflatedBroadcastChannel<V>()

    override suspend fun get(key: K): V? {
        return dao.get(key)?.let(fromEntity)
    }

    override suspend fun get(keys: List<K>): List<V> {
        return dao.get(keys).map { fromEntity(it) }
    }

    override fun getStream(key: K): Flow<V> {
        return dao.getStream(key)
            .distinctUntilChanged()
            .map { fromEntity(it) }
    }

    override fun getInsertStream(): Flow<V> {
        return insertStream.asFlow()
    }

    override suspend fun getAll(): List<V> {
        return dao.getAll()
            .map(fromEntity)
    }

    override fun getAllStream(): Flow<List<V>> {
        return dao.getAllStream()
            .map { it.map(fromEntity) }
    }

    override suspend fun put(key: K, value: V): Boolean {
        return dao.put(toEntity(value))
            ?.let(fromEntity)
            ?.let { insertStream.send(it) } != null
    }

    override suspend fun put(items: Map<K, V>): Boolean {
        val updated = dao.put(items.values.map(toEntity))
        updated.map(fromEntity).forEach { insertStream.send(it) }
        return updated.isNotEmpty()
    }

    override suspend fun delete(key: K): Boolean {
        return dao.delete(key) > 0
    }
}
