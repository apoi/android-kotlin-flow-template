package com.example.app.data.repository.repository

import com.example.app.data.fetcher.SingleFetcher
import com.example.app.data.repository.SingleRepository
import com.example.app.data.store.store.ItemListStore
import com.example.app.network.result.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * ItemListRepository handles lists of items.
 */
open class ItemListRepository<in I, K, V : Any>(
    private val store: ItemListStore<I, K, V>,
    private val fetcher: SingleFetcher<List<V>>
) : SingleRepository<List<V>>() {

    override suspend fun persist(value: List<V>) {
        store.put(value)
    }

    override suspend fun getLocal(): List<V>? {
        return store.get().let {
            if (it.isEmpty()) null
            else it
        }
    }

    override fun getLocalStream(): Flow<List<V>> {
        return store.getStream()
    }

    override suspend fun fetchRemote(): ApiResult<List<V>> {
        return fetcher.fetch()
    }
}
