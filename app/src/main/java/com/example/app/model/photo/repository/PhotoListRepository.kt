package com.example.app.model.photo.repository

import com.example.app.data.api.SingleApi
import com.example.app.data.repository.ItemListRepository
import com.example.app.model.photo.Photo
import com.example.app.model.photo.store.PhotoListStore
import com.example.app.network.PhotoApi

class PhotoListRepository(
    store: PhotoListStore,
    photoApi: PhotoApi
) : ItemListRepository<String, Int, Photo>(
    store, SingleApi(photoApi::get)
)
