package com.example.app.data.repository

import com.example.app.data.api.SingleApi
import com.example.app.data.store.store.ItemListStore
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * ItemListRepository handles lists of items.
 */
open class ItemListRepository<I, K, V : Any>(
    private val store: ItemListStore<I, K, V>,
    private val api: SingleApi<List<V>>
) : Repository<I, List<V>>() {

    override suspend fun persist(value: List<V>) {
        store.put(value)
    }

    override suspend fun getLocal(key: I): List<V>? {
        return store.get().let {
            if (it.isEmpty()) null
            else it
        }
    }

    override fun getLocalStream(key: I): Flow<List<V>> {
        return store.getStream()
    }

    override suspend fun fetchRemote(key: I): Result<List<V>> {
        return api.fetch()
    }
}
