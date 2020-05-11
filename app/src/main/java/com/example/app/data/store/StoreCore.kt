package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

interface StoreCore<K, V> {

    suspend fun get(key: K): V?

    fun getStream(key: K): Flow<V>

    suspend fun getAll(): List<V>

    fun getAllStream(): Flow<V>

    suspend fun put(key: K, value: V): Boolean

    suspend fun delete(key: K): Boolean

    // Merges the values and returns flag whether the return value differs from old value
    fun mergeValues(old: V?, new: V, merge: (V, V) -> V): Pair<V, Boolean> {
        return when (old) {
            null -> Pair(new, true)
            new -> Pair(new, false)
            else -> {
                val c = merge.invoke(old, new)
                Pair(c, old != c)
            }
        }
    }

    companion object {
        fun <V> takeNew(): (V, V) -> V {
            return { _, new -> new }
        }
    }
}
