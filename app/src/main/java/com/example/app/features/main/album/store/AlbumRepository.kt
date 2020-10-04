package com.example.app.features.main.album.store

import com.example.app.data.api.SingleApi
import com.example.app.data.pojo.Photo
import com.example.app.data.repository.ItemListRepository
import com.example.app.network.PhotoApi

class AlbumRepository(
    store: AlbumStore,
    photoApi: PhotoApi
) : ItemListRepository<String, Int, Photo>(
    store,
    SingleApi { photoApi.get() }
)
