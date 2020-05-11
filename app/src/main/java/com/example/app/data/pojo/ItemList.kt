package com.example.app.data.pojo

data class ItemList<K, V>(
    val key: K,
    val values: List<V>
)
