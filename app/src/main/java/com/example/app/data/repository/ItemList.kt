package com.example.app.data.repository

open class ItemList<out K, out V>(
    val key: K,
    val values: List<V>
)
