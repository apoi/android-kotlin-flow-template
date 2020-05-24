package com.example.app.data.repository

import com.example.app.data.api.Api
import com.example.app.data.store.Store
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow

open class DefaultRepository<K, V>(
    private val store: Store<K, V, V?>,
    private val api: Api<K, V>
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

    override suspend fun fetchRemote(key: K): Result<V> {
        return api.get(key)
    }
}
