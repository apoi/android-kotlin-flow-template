package com.example.app.feature.album.lazy.adapter

import com.example.app.domain.photo.Photo
import com.example.app.domain.photo.store.PhotoStore
import com.example.app.ui.adapter.simple.ListItem
import com.example.app.ui.adapter.simple.ListTypeFactory
import kotlinx.coroutines.flow.Flow

class LazyAlbumItemModel(
    val id: Int,
    private val store: PhotoStore
) : ListItem {

    override fun id(): Long {
        return id.toLong()
    }

    override fun type(factory: ListTypeFactory): Int {
        return factory.type(this)
    }

    fun getPhoto(): Flow<Photo> {
        return store.getStream(id)
    }
}
