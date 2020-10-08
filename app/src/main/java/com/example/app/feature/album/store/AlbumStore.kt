package com.example.app.feature.album.store

import com.example.app.data.model.Photo
import com.example.app.data.repository.ItemList
import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.ItemListStore

class AlbumStore(
    indexStoreCore: StoreCore<String, ItemList<String, Int>>,
    photoStoreCore: StoreCore<Int, Photo>
) : ItemListStore<String, Int, Photo>(
    indexKey = ALBUM_KEY,
    keyForValue = Photo::id,
    indexCore = indexStoreCore,
    valueCore = photoStoreCore
) {

    companion object {
        const val ALBUM_KEY = "album"
    }
}
