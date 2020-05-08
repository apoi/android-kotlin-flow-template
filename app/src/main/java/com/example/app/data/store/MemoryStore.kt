package com.example.app.data.store

import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class MemoryStore<T, U> : Store<T, U> {

    private val values = ConcurrentHashMap<T, ConflatedBroadcastChannel<U>>()

    override fun get(key: T): Flow<U> {
        return getChannel(key).asFlow()
    }

    override suspend fun put(key: T, value: U) {
        getChannel(key).send(value)
    }

    private fun getChannel(key: T): ConflatedBroadcastChannel<U> {
        values.putIfAbsent(key, ConflatedBroadcastChannel())
        return values.get(key)!!
    }
}
