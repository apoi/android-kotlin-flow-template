package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

interface Store<T, U> {

    fun get(key: T): Flow<U>

    suspend fun put(key: T, value: U)
}
