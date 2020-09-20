package com.example.app.data.store.core

import com.example.app.data.store.StoreCore
import com.example.app.data.store.StoreCore.Companion.takeNew
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
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
    private val stream = ConflatedBroadcastChannel<V>()

    // Listeners for values
    private val listeners = ConcurrentHashMap<K, ConflatedBroadcastChannel<V>>()

    override suspend fun get(key: K): V? {
        return cache[key]
    }

    override fun getStream(key: K): Flow<V> {
        return getOrCreateChannel(key).asFlow()
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

    private fun getOrCreateChannel(key: K): BroadcastChannel<V> {
        // Channel already exists, just return
        listeners[key]?.let {
            return@getOrCreateChannel it
        }

        // Doesn't exist, create new channel and init with content
        return ConflatedBroadcastChannel<V>().also { channel ->
            cache[key]?.let { channel.offer(it) }
            listeners.putIfAbsent(key, channel)
        }
    }
}
