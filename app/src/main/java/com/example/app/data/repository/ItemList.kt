package com.example.app.data.repository

data class ItemList<out K, out V>(
    val key: K,
    val values: List<V>
)
