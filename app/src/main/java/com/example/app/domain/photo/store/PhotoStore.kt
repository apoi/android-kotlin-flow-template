package com.example.app.domain.photo.store

import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.DefaultStore
import com.example.app.domain.photo.Photo

class PhotoStore(
    photoStoreCore: StoreCore<Int, Photo>
) : DefaultStore<Int, Photo>(photoStoreCore, Photo::id)
