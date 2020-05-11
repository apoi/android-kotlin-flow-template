package com.example.app.network.result

import org.threeten.bp.ZonedDateTime

sealed class Result<out T> {

    data class Success<out T>(val value: T?, val timestamp: ZonedDateTime) : Result<T>()

    data class HttpError(val code: Int, val error: String) : Result<Nothing>()

    data class NetworkError(val error: String) : Result<Nothing>()

    data class UnknownError(val error: String) : Result<Nothing>()
}
