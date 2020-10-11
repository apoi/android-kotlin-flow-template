package com.example.app.model.photo.store

import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.DefaultStore
import com.example.app.model.photo.Photo

class PhotoStore(
    photoStoreCore: StoreCore<Int, Photo>
) : DefaultStore<Int, Photo>(photoStoreCore, Photo::id)
