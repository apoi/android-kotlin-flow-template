package com.example.app.feature.album.store

import com.example.app.data.api.SingleApi
import com.example.app.data.model.Photo
import com.example.app.data.repository.ItemListRepository
import com.example.app.network.PhotoApi

class AlbumRepository(
    store: AlbumStore,
    photoApi: PhotoApi
) : ItemListRepository<String, Int, Photo>(
    store, SingleApi(photoApi::get)
)
