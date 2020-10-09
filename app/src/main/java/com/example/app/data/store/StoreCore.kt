package com.example.app.data.store

import kotlinx.coroutines.flow.Flow

/**
 * StoreCore is the underlying persistence mechanism of a store. It is not mandatory for a store to
 * use one, but the default implementations and base classes all use StoreCores for modularity.
 *
 * The idea behind StoreCore is that store logic is the higher-level part, whereas StoreCore is a
 * simple container that knows how to persist data in some form. This could be, for instance,
 * program memory or a content provider, but it could as well be direct disk I/O or
 * Android SharedPreferences.
 *
 * @param <K> Type of keys
 * @param <V> Type of values
 * @param <R> Type of empty values
 */
interface StoreCore<in K, V> {

    /**
     * Takes a key and returns the matching value, or null if the value isn't stored.
     *
     * @return Value matching the key, or null.
     */
    suspend fun get(key: K): V?

    /**
     * Takes a key and returns a Flow that emits any matching current item, and all future items
     * with the key.
     *
     * @param key Key for the stream of data items.
     * @return Flow emitting the current and all future values for the key.
     */
    fun getStream(key: K): Flow<V>

    /**
     * Returns all values currently stored values.
     *
     * @return All stored values-
     */
    suspend fun getAll(): List<V>

    /**
     * Returns a Flow that emits all _future_ items that are put into the core.
     *
     * @return Flow that does not immediately return anything, but emits all future items that are
     * put into the core.
     */
    fun getAllStream(): Flow<V>

    /**
     * Takes an identifier to be deleted, and returns success status of the operation.
     *
     * @param key Key of the persisted item.
     * @return True if value was deleted, and false otherwise.
     */
    suspend fun put(key: K, value: V): Boolean

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
    fun mergeValues(old: V?, new: V, merge: (V, V) -> V): Pair<V, Boolean> {
        return when (old) {
            null -> Pair(new, true)
            new -> Pair(new, false)
            else -> {
                val c = merge.invoke(old, new)
                Pair(c, old != c)
            }
        }
    }

    companion object {
        fun <V> takeNew(): (V, V) -> V {
            return { _, new -> new }
        }
    }
}
