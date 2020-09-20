package com.example.app.features.main.list

import com.example.app.data.api.SingleApi
import com.example.app.data.pojo.Photo
import com.example.app.data.repository.ItemListRepository
import com.example.app.network.PhotoApi

class ListRepository(
    store: ListStore,
    photoApi: PhotoApi
) : ItemListRepository<String, Int, Photo>(store, SingleApi(photoApi::get))
