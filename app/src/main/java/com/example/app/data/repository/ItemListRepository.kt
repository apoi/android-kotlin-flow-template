package com.example.app.data.repository

import com.example.app.data.api.SingleApi
import com.example.app.data.store.store.ItemListStore
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow

open class ItemListRepository<IndexKey, ValueKey, Value : Any>(
    private val store: ItemListStore<IndexKey, ValueKey, Value>,
    private val api: SingleApi<List<Value>>
) : Repository<IndexKey, List<Value>>() {

    override suspend fun persist(value: List<Value>) {
        store.put(value)
    }

    override suspend fun getLocal(key: IndexKey): List<Value>? {
        return store.get().let {
            if (it.isEmpty()) null
            else it
        }
    }

    override fun getLocalStream(key: IndexKey): Flow<List<Value>> {
        return store.getStream()
    }

    override suspend fun fetchRemote(key: IndexKey): Result<List<Value>> {
        return api.fetch()
    }
}
