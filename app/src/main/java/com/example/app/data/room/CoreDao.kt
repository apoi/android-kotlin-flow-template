package com.example.app.data.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore

abstract class CoreDao<K, V>(
    private val getKey: (V) -> K,
    private val merger: Merger<V>
) {

    protected suspend fun getBatch(
        keys: List<K>,
        get: suspend (List<K>) -> List<V>
    ): List<V> {
        return keys.chunked(BATCH_SIZE)
            .map { get(it) }
            .flatten()
    }

    protected suspend fun put(
        value: V,
        get: suspend (K) -> V?
    ): Boolean {
        val oldValue = get(getKey(value))
        val (newValue, valuesDiffer) = StoreCore.merge(oldValue, value, merger)

        if (!valuesDiffer) {
            return false
        }

        put(newValue)
        return true
    }

    protected suspend fun putBatch(
        values: List<V>,
        get: suspend (List<K>) -> List<V>
    ): List<V> {
        return values.chunked(BATCH_SIZE)
            .map { processBatch(it, get) }
            .flatten()
    }

    private suspend fun processBatch(
        values: List<V>,
        get: suspend (List<K>) -> List<V>
    ): List<V> {
        val oldValues = get(values.map(getKey))
            .map { Pair(getKey(it), it) }
            .toMap()

        val newValues = values
            .map { StoreCore.merge(oldValues[getKey(it)], it, merger) }
            .filter { it.second } // Take only if value changed
            .map { it.first } // Merged values will be inserted

        if (newValues.isEmpty()) {
            return emptyList()
        }

        put(newValues)
        return newValues
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun put(value: V)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun put(values: List<V>)
}
