package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Store for a single item. Useful for representing lists of values.
 *
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */
interface SingleStore<K, V> {

    suspend fun get(): V

    fun getStream(): Flow<V>

    suspend fun put(value: V): Boolean

    suspend fun delete(): Boolean
}
