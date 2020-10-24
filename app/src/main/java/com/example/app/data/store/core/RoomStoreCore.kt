package com.example.app.data.store.core

import com.example.app.data.store.StoreCore
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Shared implementation for StoreCores with Room as the backing store.
 *
 * @param <K> Type of keys.
 * @param <V> Type of values. Value is the domain model.
 * @param <E> Type of entities. Entity is the database representation of value.
 */
abstract class RoomStoreCore<K, V, E>(
    // Mapper from entity to value
    private val fromEntity: (E) -> V,
    // Mapper from value to entity
    private val toEntity: (V) -> E,
    private val coreProxy: StoreCore<K, E>
) : StoreCore<K, V> {

    private val putStream = ConflatedBroadcastChannel<V>()

    private val deleteStream = ConflatedBroadcastChannel<K>()

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
            ?.also { putStream.send(it) }
    }

    override suspend fun put(items: Map<K, V>): List<V> {
        return coreProxy.put(items.mapValues { toEntity(it.value) })
            .map(fromEntity)
            .also { values -> values.forEach { putStream.send(it) } }
    }

    override fun getPutStream(): Flow<V> {
        return putStream.asFlow()
    }

    override suspend fun delete(key: K): Boolean {
        return coreProxy.delete(key)
            .also { if (it) deleteStream.send(key) }
    }

    override fun getDeleteStream(): Flow<K> {
        return deleteStream.asFlow()
    }
}
