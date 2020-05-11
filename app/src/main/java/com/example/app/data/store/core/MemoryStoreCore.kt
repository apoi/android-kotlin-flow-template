package com.example.app.data.store.core

import com.example.app.data.store.StoreCore
import com.example.app.data.store.StoreCore.Companion.takeNew
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.util.concurrent.ConcurrentHashMap

/**
 * Base memory store core.
 */
open class MemoryStoreCore<K, V>(
    private val merge: (V, V) -> V = takeNew()
) : StoreCore<K, V> {

    // All values in the store
    private val cache = ConcurrentHashMap<K, V>()

    // Flow of values from the
    private val stream = BroadcastChannel<V>(1)

    // Listeners for values
    private val listeners = ConcurrentHashMap<K, BroadcastChannel<V>>()

    override suspend fun get(key: K): V? {
        return cache[key]
    }

    override fun getStream(key: K): Flow<V> {
        return getOrCreateListener(key).asFlow()
    }

    override suspend fun getAll(): List<V> {
        return cache.values.toList()
    }

    override fun getAllStream(): Flow<V> {
        return stream.asFlow()
    }

    override suspend fun put(key: K, value: V): Boolean {
        val (newValue, valuesDiffer) = mergeValues(cache[key], value, merge)

        if (!valuesDiffer) {
            // Data is already up to date
            return false
        }

        cache[key] = newValue
        stream.send(newValue)
        listeners[key]?.send(newValue)

        return true
    }

    override suspend fun delete(key: K): Boolean {
        return cache.remove(key) != null
    }

    private fun getOrCreateListener(key: K): BroadcastChannel<V> {
        // Channel already exists, just return
        listeners[key]?.let { return@getOrCreateListener it }

        // Doesn't exist, create new channel
        listeners.putIfAbsent(key, BroadcastChannel(1))
        return listeners[key]!!
    }
}
