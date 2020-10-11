package com.example.app.model.photo.store

import com.example.app.data.repository.ItemList
import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.ItemListStore
import com.example.app.model.photo.Photo

class PhotoListStore(
    indexStoreCore: StoreCore<String, ItemList<String, Int>>,
    photoStoreCore: StoreCore<Int, Photo>
) : ItemListStore<String, Int, Photo>(
    indexKey = KEY,
    keyForValue = Photo::id,
    indexCore = indexStoreCore,
    valueCore = photoStoreCore
) {

    companion object {
        private const val KEY = "photoList"
    }
}
