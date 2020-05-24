package com.example.app.util

inline fun <T> T?.ifNull(block: () -> Unit): T? {
    if (this == null) block()
    return this
}
