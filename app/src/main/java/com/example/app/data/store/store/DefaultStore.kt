package com.example.app.data.store.store

import com.example.app.data.store.Store
import com.example.app.data.store.StoreCore
import kotlinx.coroutines.flow.Flow

open class DefaultStore<K, V>(
    private val core: StoreCore<K, V>,
    private val getKey: (V) -> K
) : Store<K, V, V?> {

    override suspend fun get(key: K): V? {
        return core.get(key)
    }

    override fun getStream(key: K): Flow<V> {
        return core.getStream(key)
    }

    override suspend fun put(value: V): Boolean {
        return core.put(getKey(value), value)
    }

    override suspend fun delete(key: K): Boolean {
        return core.delete(key)
    }
}
