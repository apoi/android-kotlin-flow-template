package com.example.app.feature.details.store

import com.example.app.data.model.Photo
import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.DefaultStore

class DetailsStore(
    photoStoreCore: StoreCore<Int, Photo>
) : DefaultStore<Int, Photo>(photoStoreCore, Photo::id)
