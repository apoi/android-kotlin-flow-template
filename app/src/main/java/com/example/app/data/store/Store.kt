package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Store interface.
 *
 * @param <K> Type of keys
 * @param <V> Type of values
 * @param <R> Type of empty values
 */
interface Store<K, V, R> {

    suspend fun get(key: K): R

    fun getStream(key: K): Flow<V>

    suspend fun put(value: V): Boolean

    suspend fun delete(key: K): Boolean
}
