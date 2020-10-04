package com.example.app.features.main.album.store

import com.example.app.data.pojo.Photo
import com.example.app.data.store.core.MemoryStoreCore
import com.example.app.data.store.store.ItemListStore

class AlbumStore : ItemListStore<String, Int, Photo>(
    indexKey = PHOTO_INDEX_KEY,
    keyForValue = Photo::id,
    indexCore = MemoryStoreCore(),
    valueCore = MemoryStoreCore()
) {
    companion object {
        const val PHOTO_INDEX_KEY = "photos"
    }
}
