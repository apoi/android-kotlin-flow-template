package com.example.app.data.repository

import com.example.app.data.state.State
import com.example.app.network.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * Repository binds [Store] and [Api] together to a data source that handles
 * data fetching and persisting.
 */
abstract class Repository<in K, V> {

    /**
     * Returns the current state without fetching.
     */
    suspend fun get(key: K): State<V> {
        return getLocal(key)
            ?.let { State.Success(it) }
            ?: State.Empty
    }

    /**
     * Returns a stream of data with fetching if current value is invalid.
     */
    fun getStream(key: K, validator: Validator<V> = Accept()): Flow<State<V>> {
        return flow {
            emit(State.Loading)

            // Invalid value triggers fetch. It must not trigger clearing of the
            // value as the value may still be valid for another consumer with
            // a different validator.
            val isValid = getLocal(key)?.let(validator::validate) == true

            if (!isValid) {
                when (val response = fetchRemote(key)) {
                    is Result.Success -> {
                        if (response.value != null) {
                            // Response is persisted to be emitted later
                            persist(response.value)
                        } else {
                            // Empty states don't go through persistence layer
                            emit(State.Empty)
                        }
                    }
                    // State can be expanded for more detailed error types
                    is Result.HttpError -> emit(State.Error(response.error))
                    is Result.NetworkError -> emit(State.Error(response.error))
                    is Result.UnknownError -> emit(State.Error(response.error))
                }
            }

            // Emit current and future values. The values need to be still validated
            // as otherwise this may emit the existing value in case of failed fetch.
            emitAll(
                getLocalStream(key)
                    .filter { validator.validate(it) }
                    .map { State.Success(it) }
            )
        }
    }

    protected abstract suspend fun persist(value: V)

    protected abstract suspend fun getLocal(key: K): V?

    protected abstract fun getLocalStream(key: K): Flow<V>

    protected abstract suspend fun fetchRemote(key: K): Result<V>
}
