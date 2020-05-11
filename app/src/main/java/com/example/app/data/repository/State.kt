package com.example.app.data.repository

import org.threeten.bp.ZonedDateTime

sealed class State<out T> {

    object Loading : State<Nothing>()

    data class Success<out T>(val value: T) : State<T>()

    data class Error(val error: String) : State<Nothing>()
}
