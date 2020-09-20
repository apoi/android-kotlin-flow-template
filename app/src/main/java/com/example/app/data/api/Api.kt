package com.example.app.data.api

import com.example.app.network.result.Result

class Api<K, V>(private val fetcher: (K) -> Result<V>) {
    suspend fun fetch(key: K) = fetcher.invoke(key)
}
