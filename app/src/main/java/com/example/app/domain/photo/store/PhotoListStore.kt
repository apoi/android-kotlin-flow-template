package com.example.app.domain.photo.store

import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.ItemListStore
import com.example.app.domain.idlist.IdList
import com.example.app.domain.photo.Photo

class PhotoListStore(
    indexStoreCore: StoreCore<String, IdList>,
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
