package com.example.app.data.repository

import com.example.app.data.fetcher.Fetcher
import com.example.app.data.store.Store
import com.example.app.network.result.ApiResult
import kotlinx.coroutines.flow.Flow

open class DefaultRepository<in K, V>(
    private val store: Store<K, V>,
    private val fetcher: Fetcher<K, V>
) : Repository<K, V>() {

    override suspend fun persist(value: V) {
        store.put(value)
    }

    override suspend fun getLocal(key: K): V? {
        return store.get(key)
    }

    override fun getLocalStream(key: K): Flow<V> {
        return store.getStream(key)
    }

    override suspend fun fetchRemote(key: K): ApiResult<V> {
        return fetcher.fetch(key)
    }
}
