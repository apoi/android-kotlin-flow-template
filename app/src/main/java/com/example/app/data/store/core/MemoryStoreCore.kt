package com.example.app.data.store.core

import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import com.example.app.data.store.StoreCore.Companion.takeNew
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

/**
 * StoreCore with memory as the backing store. This store does not persist anything across
 * application restarts.
 */
open class MemoryStoreCore<K, V>(
    private val merger: Merger<V> = takeNew()
) : StoreCore<K, V> {

    // All values in the store
    private val cache = ConcurrentHashMap<K, V>()

    // Flow of all values
    private val stream = ConflatedBroadcastChannel<V>()

    // Listeners for given keys
    private val listeners = ConcurrentHashMap<K, ConflatedBroadcastChannel<V>>()

    // Guard for synchronizing all writing methods
    private val lock = ReentrantLock()

    override suspend fun get(key: K): V? {
        return cache[key]
    }

    override suspend fun get(keys: List<K>): List<V> {
        return keys.mapNotNull { cache[it] }
    }

    override fun getStream(key: K): Flow<V> {
        return lock.withLock {
            getOrCreateChannel(key).asFlow()
        }
    }

    override fun getInsertStream(): Flow<V> {
        return stream.asFlow()
    }

    override suspend fun getAll(): List<V> {
        return cache.values.toList()
    }

    override fun getAllStream(): Flow<List<V>> {
        return stream.asFlow()
            .map { getAll() }
    }

    override suspend fun put(key: K, value: V): V? {
        lock.lock()

        val (newValue, valuesDiffer) = mergeValues(cache[key], value, merger)

        if (!valuesDiffer) {
            // Data is already up to date
            lock.unlock()
            return null
        }

        cache[key] = newValue
        stream.send(newValue)
        listeners[key]?.send(newValue)

        lock.unlock()
        return newValue
    }

    override suspend fun put(items: Map<K, V>): List<V> {
        return items.mapNotNull { (key, value) -> put(key, value) }
    }

    override suspend fun delete(key: K): Boolean {
        return lock.withLock {
            cache.remove(key) != null
        }
    }

    private fun getOrCreateChannel(key: K): BroadcastChannel<V> {
        // Just return if channel already exists
        listeners[key]?.let {
            return@getOrCreateChannel it
        }

        // Doesn't exist, create new channel and init if value exist
        return ConflatedBroadcastChannel<V>().also { channel ->
            cache[key]?.let(channel::offer)
            listeners.putIfAbsent(key, channel)
        }
    }
}
