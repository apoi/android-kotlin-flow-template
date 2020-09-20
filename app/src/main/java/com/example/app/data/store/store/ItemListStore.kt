package com.example.app.data.store.store

import com.example.app.data.pojo.ItemList
import com.example.app.data.store.SingleStore
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Store for lists of items. Keeps index of the items in one core,
 * and items themselves in another core. This allows each item to
 * exist in multiple indexes and be queried independently.
 */
open class ItemListStore<IndexKey, ValueKey, Value : Any>(
    private val indexKey: IndexKey,
    private val keyForValue: (Value) -> ValueKey,
    private val indexCore: StoreCore<IndexKey, ItemList<IndexKey, ValueKey>>,
    private val valueCore: StoreCore<ValueKey, Value>
) : SingleStore<List<Value>, List<Value>> {

    override suspend fun get(): List<Value> {
        return indexCore.get(indexKey)
            ?.values
            ?.mapNotNull { valueCore.get(it) }
            ?: emptyList()
    }

    override fun getStream(): Flow<List<Value>> {
        return indexCore.getStream(indexKey)
            .map { index -> index.values }
            .map { keys -> keys.mapNotNull { valueCore.get(it) } }
    }

    /**
     * Put all values to store, and add index to keep track
     * of all the values.
     */
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun put(values: List<Value>): Boolean {
        // Put values first in case there's a listener for the index. This way values
        // already exist for any listeners to query.
        val valueChanged = values.map { value -> valueCore.put(keyForValue(value), value) }.any()
        val indexChanged = indexCore.put(indexKey, ItemList(indexKey, values.map(keyForValue)))

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
