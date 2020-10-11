package com.example.app.data.room

import androidx.room.Insert
import com.example.app.model.photo.store.PhotoEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface matching StoreCore operations.
 *
 * Room can't handle parent class with abstract methods.
 */
abstract class CoreDao<in K, V> {

    open suspend fun get(key: K): V? = TODO()

    open fun getStream(key: K): Flow<V> = TODO()

    open suspend fun getAll(): List<V> = TODO()

    open fun getAllStream(): Flow<List<V>> = TODO()

    open suspend fun put(key: K, value: V): Boolean = TODO()

    @Insert
    abstract fun put(value: PhotoEntity)

    open suspend fun delete(key: K): Int = TODO()
}
