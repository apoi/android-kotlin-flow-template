package com.example.app.features.main.details.store

import com.example.app.data.pojo.Photo
import com.example.app.data.store.StoreCore
import com.example.app.data.store.store.DefaultStore

class DetailsStore(
    photoStoreCore: StoreCore<Int, Photo>
) : DefaultStore<Int, Photo>(photoStoreCore, Photo::id)
