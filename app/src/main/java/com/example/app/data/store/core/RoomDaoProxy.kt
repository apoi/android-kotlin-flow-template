package com.example.app.data.store.core

import com.example.app.data.store.StoreCore

/**
 * Interface for accessing a DAO that implements methods required for a StoreCore. DAO proxies are
 * needed as Room DAOs can't have abstract parent classes that would define the core interface.
 */
abstract class RoomDaoProxy<K, V> : StoreCore<K, V> {

    override fun getPutStream() = error("Not implemented")

    override fun getDeleteStream() = error("Not implemented")
}
