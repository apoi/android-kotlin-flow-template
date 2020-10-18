package com.example.app.model.photo.store

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.app.data.room.CoreDao
import com.example.app.data.store.Merger
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PhotoDao : CoreDao<Int, PhotoEntity>(
    PhotoEntity::id,
    PhotoEntity.merger
) {

    @Query("SELECT * FROM photos WHERE id=:key")
    abstract suspend fun get(key: Int): PhotoEntity?

    @Transaction
    open suspend fun get(keys: List<Int>): List<PhotoEntity> {
        return getBatch(keys, ::getList)
    }

    @Query("SELECT * FROM photos WHERE id IN (:keys)")
    abstract suspend fun getList(keys: List<Int>): List<PhotoEntity>

    @Query("SELECT * FROM photos WHERE id=:key")
    abstract fun getStream(key: Int): Flow<PhotoEntity>

    @Query("SELECT * FROM photos")
    abstract suspend fun getAll(): List<PhotoEntity>

    @Query("SELECT * FROM photos")
    abstract fun getAllStream(): Flow<List<PhotoEntity>>

    @Transaction
    open suspend fun put(
        value: PhotoEntity,
        merger: Merger<PhotoEntity>
    ): PhotoEntity? {
        return put(value, ::get)
    }

    @Transaction
    open suspend fun put(
        values: List<PhotoEntity>,
        merger: Merger<PhotoEntity>
    ): List<PhotoEntity> {
        return putBatch(values, ::get)
    }

    @Query("DELETE FROM photos WHERE id=:key")
    abstract suspend fun delete(key: Int): Int
}
