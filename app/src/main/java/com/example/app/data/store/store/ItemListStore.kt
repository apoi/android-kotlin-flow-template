package com.example.app.data.store.store

import com.example.app.data.repository.repository.ItemList
import com.example.app.data.store.SingleStore
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Store for lists of items. Keeps index of the items in one core, and items themselves in another
 * core. This allows each item to exist in multiple indexes and be queried independently.
 *
 * @param <I> Type of index keys.
 * @param <K> Type of keys.
 * @param <V> Type of values.
 */
open class ItemListStore<I, K, V : Any>(
    private val indexKey: I,
    private val getKey: (V) -> K,
    private val indexCore: StoreCore<I, ItemList<I, K>>,
    private val valueCore: StoreCore<K, V>
) : SingleStore<List<V>> {

    override suspend fun get(): List<V> {
        return withContext(Dispatchers.IO) {
            indexCore.get(indexKey)?.values
                ?.let { valueCore.get(it) }
                ?: emptyList()
        }
    }

    override fun getStream(): Flow<List<V>> {
        return indexCore.getStream(indexKey)
            .map { index -> index.values }
            .map(valueCore::get)
            .flowOn(Dispatchers.IO)
    }

    /**
     * Put all values to store, and add index to keep track of all the values.
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun put(values: List<V>): Boolean {
        return withContext(Dispatchers.IO) {
            // Put values first in case there's a listener for the index. This way values
            // already exist for any listeners to query.
            val newValues = values
                .map { value -> Pair(getKey(value), value) }
                .let { items -> valueCore.put(items.toMap()) }

            val indexChanged = indexCore.put(indexKey, ItemList(indexKey, values.map(getKey)))

            newValues.isNotEmpty() || indexChanged != null
        }
    }

    /**
     * Deletes key from the index store. Does not delete any of the values,
     * as they may be referenced in other indexes.
     */
    override suspend fun delete(): Boolean {
        return withContext(Dispatchers.IO) {
            indexCore.delete(indexKey)
        }
    }
}
