package com.example.app.data.store.store

import com.example.app.data.repository.ItemList
import com.example.app.data.store.SingleStore
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * Store for lists of items. Keeps index of the items in one core, and items themselves in another
 * core. This allows each item to exist in multiple indexes and be queried independently.
 *
 * @param
 */
open class ItemListStore<I, K, V : Any>(
    private val indexKey: I,
    private val keyForValue: (V) -> K,
    private val indexCore: StoreCore<I, ItemList<I, K>>,
    private val valueCore: StoreCore<K, V>
) : SingleStore<List<V>, List<V>> {

    override suspend fun get(): List<V> {
        val time1 = System.currentTimeMillis()

        val values = indexCore.get(indexKey)
            ?.values
            ?.mapNotNull { valueCore.get(it) }
            ?: emptyList()

        val time2 = System.currentTimeMillis()
        val time3 = time2 - time1
        Timber.w("GET: $time3 MILLIS")

        return values
    }

    override fun getStream(): Flow<List<V>> {
        return indexCore.getStream(indexKey)
            .map { index -> index.values }
            .map { keys ->
                val time1 = System.currentTimeMillis()

                val value = keys.mapNotNull { valueCore.get(it) }

                val time2 = System.currentTimeMillis()
                val time3 = time2 - time1
                Timber.w("STREAM: $time3 MILLIS")

                value
            }
    }

    /**
     * Put all values to store, and add index to keep track of all the values.
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun put(values: List<V>): Boolean {
        // Put values first in case there's a listener for the index. This way values
        // already exist for any listeners to query.
        val time1 = System.currentTimeMillis()

        val valueChanged = values
            .map { value -> Pair(keyForValue(value), value) }
            .let { items -> valueCore.put(items.toMap()) }

        val indexChanged = indexCore.put(indexKey, ItemList(indexKey, values.map(keyForValue)))

        val time2 = System.currentTimeMillis()
        val time3 = time2 - time1
        Timber.w("INSERT: $time3 MILLIS")

        return valueChanged || indexChanged
    }

    /**
     * Deletes key from the index store. Does not delete any of the values,
     * as they may be referenced in other indexes.
     */
    override suspend fun delete(): Boolean {
        return indexCore.delete(indexKey)
    }
}
