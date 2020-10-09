package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

/**
 * Default interface for a store. A store acts as a data container, in which all data
 * items are identified with an id that can be deduced from the item itself. Usually this would be
 * done through a function such as U getId(T item), but it can be defined in the store
 * implementation itself.
 *
 * @param <T> Type of the id used in this store.
 * @param <U> Type of the data this store contains.
 * @param <R> Non-null type or wrapper for the data this store contains.
 */
interface Store<in K, V, out R> {

    /**
     * Returns value for given key, or empty value if key doesn't exist in store.
     */
    suspend fun get(key: K): R

    /**
     * Returns stream of future values for given key, starting with the current
     * value if one exists.
     */
    fun getStream(key: K): Flow<V>

    /**
     * Puts the value to the store.
     *
     * @return True if operation was successful, otherwise false.
     */
    suspend fun put(value: V): Boolean

    /**
     * Deletes value matching the key from the store.
     *
     * @return True if operation was successful, otherwise false.
     */
    suspend fun delete(key: K): Boolean
}
