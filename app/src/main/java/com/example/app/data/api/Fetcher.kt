package com.example.app.data.api

import com.example.app.network.result.ApiResult
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

/**
 * Fetcher fetches a remote value for a specific type. Deduplicates calls so that all new
 * invocations made during an existing fetch operation will suspend and wait for the result of
 * the ongoing call.
 */
class Fetcher<in K, out V>(private val api: suspend (K) -> ApiResult<V>) {

    // Map of suspended fetcher invocations waiting for result
    private val map = ConcurrentHashMap<K, List<Continuation<ApiResult<V>>>>()

    // Lock for guaranteeing correct map modification order
    private val lock = ReentrantLock()

    suspend fun fetch(key: K): ApiResult<V> {
        lock.lock()

        val continuations = map[key]

        return if (continuations == null) {
            // No requests yet, invoke the API now. Mark ongoing operation with an empty
            // list so that any other invocations will take the suspending branch.
            map[key] = emptyList()
            lock.unlock()
            fetchAndResume(key)
        } else {
            // Request is already ongoing. Suspend and add a listener for the result.
            suspendCoroutine { continuation ->
                map[key] = continuations + continuation
                lock.unlock()
            }
        }
    }

    private suspend fun fetchAndResume(key: K): ApiResult<V> {
        val result = api.invoke(key)

        lock.withLock {
            map.remove(key)
                ?.forEach { it.resumeWith(Result.success(result)) }
        }

        return result
    }
}

/**
 * Fetcher that has no key for the Api.
 */
class SingleFetcher<out V>(private val api: suspend () -> ApiResult<V>) {

    private val fetcher = Fetcher<Int, V> { api.invoke() }

    suspend fun fetch(): ApiResult<V> {
        return fetcher.fetch(0)
    }
}
