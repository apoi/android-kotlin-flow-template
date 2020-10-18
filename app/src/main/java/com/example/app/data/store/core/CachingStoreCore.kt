package com.example.app.data.store.core

import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * StoreCore that takes another core as parameter and adds a caching layer in front of it. This is
 * useful especially if the other core is slow, such as a Room backed core.
 */
class CachingStoreCore<K, V>(
    private val persistingCore: StoreCore<K, V>,
    private val getKey: (V) -> K,
    merger: Merger<V> = StoreCore.takeNew()
) : StoreCore<K, V> {

    private val cacheCore = MemoryStoreCore<K, V>(merger)
    private val lock = Mutex(false)

    init {
        // Permanent subscription to all updates to keep cache up-to-date
        GlobalScope.launch(Dispatchers.Default) {
            persistingCore
                .getInsertStream()
                .collect { value ->
                    lock.withLock { cacheCore.put(getKey(value), value) }
                }
        }
    }

    override suspend fun get(key: K): V? {
        return lock.withLock {
            cacheCore.get(key)
                ?: persistingCore.get(key)?.also { value ->
                    cacheCore.put(key, value)
                }
        }
    }

    override suspend fun get(keys: List<K>): List<V> {
        val cached = cacheCore.get(keys)
        if (keys.size == cached.size) return cached

        return lock.withLock {
            persistingCore.get(keys)
                .also { cacheCore.put(it.associateBy(getKey)) }
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
        return lock.withLock {
            persistingCore.delete(key)
                .also { cacheCore.delete(key) }
        }
    }
}
