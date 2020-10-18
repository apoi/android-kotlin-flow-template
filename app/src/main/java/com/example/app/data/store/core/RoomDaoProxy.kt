package com.example.app.data.store.core

import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing a DAO that implements methods required for a StoreCore. DAO proxies are
 * needed as Room DAOs can't have abstract parent classes that would define the interface.
 *
 * This interface is different from StoreCore in that the put methods return the merged value.
 */
interface RoomDaoProxy<in K, E> {

    suspend fun get(key: K): E?

    suspend fun get(keys: List<K>): List<E>

    fun getStream(key: K): Flow<E>

    suspend fun getAll(): List<E>

    fun getAllStream(): Flow<List<E>>

    suspend fun put(value: E): E?

    suspend fun put(values: List<E>): List<E>

    suspend fun delete(key: K): Int
}
