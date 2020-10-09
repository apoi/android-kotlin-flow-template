package com.example.app.data.repository

interface Validator<in V> {
    fun validate(value: V): Boolean
}

class Accept<V> : Validator<V> {
    override fun validate(value: V) = true
}
