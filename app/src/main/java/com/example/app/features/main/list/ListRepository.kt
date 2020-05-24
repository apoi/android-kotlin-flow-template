package com.example.app.features.main.list

import com.example.app.data.pojo.Photo
import com.example.app.data.repository.ItemListRepository
import com.example.app.network.PhotoApi

class ListRepository(
    store: ListStore,
    api: PhotoApi
) : ItemListRepository<String, Int, Photo>(store, api)
