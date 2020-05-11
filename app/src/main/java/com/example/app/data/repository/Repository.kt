package com.example.app.data.repository

import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class Repository<T> {

    fun get() = flow {
        emit(State.Loading)
        emitAll(getLocalStream())
    }

    fun fetch() = flow {
        emit(State.Loading)
        emit(State.Success(get()))

        when (val response = fetchRemote()) {
            is Result.Success -> response.value?.let { persist(it) }
            is Result.HttpError -> emit(State.Error(response.error))
            is Result.NetworkError -> emit(State.Error(response.error))
            is Result.UnknownError -> emit(State.Error(response.error))
        }

        // Retrieve posts from persistence storage and emit
        emitAll(getLocalStream().map {
            State.Success(it)
        })
    }

    protected abstract suspend fun persist(response: T)

    protected abstract suspend fun getLocal(): T

    protected abstract fun getLocalStream(): Flow<T>

    protected abstract suspend fun fetchRemote(): Result<T>
}
