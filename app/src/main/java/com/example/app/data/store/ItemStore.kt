package com.example.app.data.store

/**
 * Store for regular items.
 */
interface ItemStore<in K, V> : Store<K, V, V?>
