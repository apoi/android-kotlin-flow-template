package com.example.app.model.photo.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Dao
abstract class PhotoDao {

    @Query("SELECT * FROM photos WHERE id=:key")
    abstract suspend fun get(key: Int): PhotoEntity?

    @Query("SELECT * FROM photos WHERE id IN (:keys)")
    abstract suspend fun get(keys: List<Int>): List<PhotoEntity>

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
    ): Boolean {
        val oldValue = get(value.id)
        val (newValue, valuesDiffer) = StoreCore.merge(oldValue, value, merger)

        if (!valuesDiffer) {
            Timber.w("${value.id}: NO CHANGE")
            // Data is already up to date
            return false
        }

        Timber.w("${value.id}: PUT")
        put(newValue)
        return true
    }

    @Transaction
    open suspend fun put(
        values: List<PhotoEntity>,
        merger: Merger<PhotoEntity>
    ): List<PhotoEntity> {
        Timber.w("PUT: ${values.size}")
        return values.chunked(999)
            .map { putBatch(it, merger) }
            .flatten()
    }

    private suspend fun putBatch(
        values: List<PhotoEntity>,
        merger: Merger<PhotoEntity>
    ): List<PhotoEntity> {
        Timber.w("BATCH: ${values.size}")

        val oldValues = get(values.map(PhotoEntity::id))
            .map { Pair(it.id, it) }
            .toMap()

        val newValues = values
            .map { StoreCore.merge(oldValues[it.id], it, merger) }
            .filter { it.second } // Take only changed values
            .map { it.first } // Merged values

        Timber.w("NEW: ${newValues.size}")

        if (newValues.isEmpty()) {
            Timber.w("LIST: NO CHANGE")
            // Data is already up to date
            return emptyList()
        }

        Timber.w("LIST: PUT $newValues")
        put(newValues)
        return newValues
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun put(value: PhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun put(values: List<PhotoEntity>)

    @Query("DELETE FROM photos WHERE id=:key")
    abstract suspend fun delete(key: Int): Int
}
