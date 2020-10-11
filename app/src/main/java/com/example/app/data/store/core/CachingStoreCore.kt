package com.example.app.data.store.core

import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CachingStoreCore<K, V>(
    private val persistingCore: StoreCore<K, V>,
    private val getKey: (V) -> K,
    merger: Merger<V> = StoreCore.takeNew()
) : StoreCore<K, V> {

    private val cacheCore = MemoryStoreCore<K, V>(merger)

    init {
        // Permanent subscription to all updates to keep cache up-to-date
        GlobalScope.launch(Dispatchers.Default) {
            persistingCore
                .getInsertStream()
                .collect { value -> cacheCore.put(getKey(value), value) }
        }
    }

    override suspend fun get(key: K): V? {
        return cacheCore.get(key)
            ?: persistingCore.get(key)?.also { value ->
                cacheCore.put(key, value)
            }
    }

    override fun getStream(key: K): Flow<V> {
        return persistingCore.getStream(key)
    }

    override fun getInsertStream(): Flow<V> {
        return persistingCore.getInsertStream()
    }

    override suspend fun getAll(): List<V> {
        return persistingCore.getAll()
    }

    override fun getAllStream(): Flow<List<V>> {
        return persistingCore.getAllStream()
    }

    override suspend fun put(key: K, value: V): Boolean {
        return persistingCore.put(key, value)
    }

    override suspend fun put(items: Map<K, V>): Boolean {
        return persistingCore.put(items)
    }

    override suspend fun delete(key: K): Boolean {
        return persistingCore.delete(key)
            .also { cacheCore.delete(key) }
    }
}
