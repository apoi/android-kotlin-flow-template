package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

typealias Merger<V> = (V?, V) -> V

/**
 * StoreCore is the underlying persistence mechanism of a store. It is not mandatory for a store to
 * use one, but the default implementations and base classes all use StoreCores for modularity.
 *
 * The idea behind StoreCore is that store logic is the higher-level part, whereas StoreCore is a
 * simple container that knows how to persist data in some form. This could be, for instance,
 * program memory or a content provider, but it could as well be direct disk I/O or
 * Android SharedPreferences.
 *
 * One StoreCore can be shared between multiple stores. StoreCore is the single source of truth
 * for the data it contains.
 *
 * @param <K> Type of keys
 * @param <V> Type of values
 */
interface StoreCore<K, V> {

    /**
     * Takes a key and returns the matching value, or null if the value isn't stored.
     *
     * @return Value matching the key, or null.
     */
    suspend fun get(key: K): V?

    /**
     * Takes a list of keys and returns list of matching values.
     *
     * @return All found values matching the keys, or empty list if no value is found.
     */
    suspend fun get(keys: List<K>): List<V>

    /**
     * Takes a key and returns a Flow that emits any matching current item, and all future items
     * with the key.
     *
     * @param key Key for the stream of data items.
     * @return Flow emitting the current and all future values for the key.
     */
    fun getStream(key: K): Flow<V>

    /**
     * Returns a Flow that emits every time an insert has been performed.
     *
     * @return Flow with values that have been updated in the store.
     */
    fun getInsertStream(): Flow<V>

    /**
     * Returns all values currently stored values.
     *
     * @return All stored values.
     */
    suspend fun getAll(): List<V>

    /**
     * Returns a Flow that emits all currently stored values, and re-emits on changes.
     *
     * @return Flow with all stored values, and new values on changes.
     */
    fun getAllStream(): Flow<List<V>>

    /**
     * Takes an identifier to be added, and returns success status of the operation.
     *
     * @param key Key of the persisted item.
     * @return True if value was added, and false otherwise.
     */
    suspend fun put(key: K, value: V): Boolean

    /**
     * Takes an identifier to be added, and returns success status of the operation.
     *
     * @param items Map of items to insert.
     * @return True if any value was added, false otherwise.
     */
    suspend fun put(items: Map<K, V>): Boolean

    /**
     * Takes a key to be deleted, and returns success status of the operation.
     *
     * @param key Key of the persisted item.
     * @return True if value was deleted, and false otherwise.
     */
    suspend fun delete(key: K): Boolean

    /**
     * Merges the values and returns flag whether the return value differs from old value.
     */
    fun mergeValues(old: V?, new: V, merger: Merger<V>): Pair<V, Boolean> {
        return merge(old, new, merger)
    }

    companion object {

        /**
         * Merge implementation. Needs to be companion for DAO access.
         */
        fun <V> merge(old: V?, new: V, merger: Merger<V>): Pair<V, Boolean> {
            return when (old) {
                null -> Pair(new, true)
                new -> Pair(new, false)
                else -> {
                    val c = merger.invoke(old, new)
                    Pair(c, old != c)
                }
            }
        }

        /**
         * Default operation for resolving merge is taking the new value.
         */
        fun <V> takeNew(): Merger<V> {
            return { _, new -> new }
        }
    }
}
