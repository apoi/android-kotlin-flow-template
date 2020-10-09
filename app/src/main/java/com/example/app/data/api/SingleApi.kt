package com.example.app.data.api

import com.example.app.network.result.Result

class SingleApi<out V>(private val fetcher: suspend () -> Result<V>) {
    suspend fun fetch() = fetcher.invoke()
}
