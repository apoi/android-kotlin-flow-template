package com.example.app.network.result

/**
 * Wrapper class for network request results.
 */
sealed class ApiResult<out T> {

    data class Success<out T>(val value: T?) : ApiResult<T>()

    data class HttpError(val code: Int, val error: String) : ApiResult<Nothing>()

    data class NetworkError(val error: String) : ApiResult<Nothing>()

    data class UnknownError(val error: String) : ApiResult<Nothing>()
}
