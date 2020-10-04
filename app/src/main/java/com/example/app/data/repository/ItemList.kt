package com.example.app.data.repository

data class ItemList<K, V>(
    val key: K,
    val values: List<V>
)
