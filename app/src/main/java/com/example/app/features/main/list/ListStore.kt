package com.example.app.features.main.list

import com.example.app.data.pojo.Photo
import com.example.app.data.store.core.MemoryStoreCore
import com.example.app.data.store.store.ItemListStore

class ListStore : ItemListStore<String, Int, Photo>(
    indexKey = PHOTO_INDEX_KEY,
    keyForValue = Photo::id,
    indexCore = MemoryStoreCore(),
    valueCore = MemoryStoreCore()
) {
    companion object {
        const val PHOTO_INDEX_KEY = "photos"
    }
}
