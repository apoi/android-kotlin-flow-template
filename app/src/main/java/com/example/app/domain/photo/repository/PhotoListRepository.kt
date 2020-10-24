package com.example.app.domain.photo.repository

import com.example.app.data.fetcher.SingleFetcher
import com.example.app.data.repository.ItemListRepository
import com.example.app.domain.photo.Photo
import com.example.app.domain.photo.store.PhotoListStore
import com.example.app.network.PhotoApi

class PhotoListRepository(
    store: PhotoListStore,
    photoApi: PhotoApi
) : ItemListRepository<String, Int, Photo>(
    store, SingleFetcher(photoApi::get)
)
