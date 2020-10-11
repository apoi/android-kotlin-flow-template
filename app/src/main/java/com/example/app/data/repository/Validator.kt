package com.example.app.data.repository

import timber.log.Timber

interface Validator<in V> {
    fun validate(value: V): Boolean
}

class Accept<V> : Validator<V> {
    override fun validate(value: V): Boolean {
        Timber.w("VALIDATE " + value)
        return true
    }
}
