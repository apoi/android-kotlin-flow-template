package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Store for a single item. Useful for representing lists of values.
 */
interface SingleStore<V, R> {

    suspend fun get(): R

    fun getStream(): Flow<V>

    suspend fun put(value: V): Boolean

    suspend fun delete(): Boolean
}
