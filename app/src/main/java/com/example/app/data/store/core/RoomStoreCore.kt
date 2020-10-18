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
    private val coreProxy: StoreCore<K, E>
) : StoreCore<K, V> {

    private val insertStream = ConflatedBroadcastChannel<V>()

    override suspend fun get(key: K): V? {
        return coreProxy.get(key)?.let(fromEntity)
    }

    override suspend fun get(keys: List<K>): List<V> {
        return coreProxy.get(keys).map { fromEntity(it) }
    }

    override fun getStream(key: K): Flow<V> {
        return coreProxy.getStream(key)
            .distinctUntilChanged()
            .map { fromEntity(it) }
    }

    override fun getInsertStream(): Flow<V> {
        return insertStream.asFlow()
    }

    override suspend fun getAll(): List<V> {
        return coreProxy.getAll()
            .map(fromEntity)
    }

    override fun getAllStream(): Flow<List<V>> {
        return coreProxy.getAllStream()
            .map { it.map(fromEntity) }
    }

    override suspend fun put(key: K, value: V): V? {
        return coreProxy.put(key, toEntity(value))
            ?.let(fromEntity)
            ?.also { insertStream.send(it) }
    }

    override suspend fun put(items: Map<K, V>): List<V> {
        return coreProxy.put(items.mapValues { toEntity(it.value) })
            .map(fromEntity)
            .also { values -> values.forEach { insertStream.send(it) } }
    }

    override suspend fun delete(key: K): Boolean {
        return coreProxy.delete(key)
    }
}
