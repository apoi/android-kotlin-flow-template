package com.example.app.feature.album.adapter

import com.example.app.data.model.Photo
import com.example.app.ui.adapter.ListItem
import com.example.app.ui.adapter.ListTypeFactory

data class AlbumItemModel(
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : ListItem {

    override fun id(): Long {
        return id.toLong()
    }

    override fun type(factory: ListTypeFactory): Int {
        return factory.type(this)
    }

    companion object {
        fun from(photo: Photo): AlbumItemModel {
            return AlbumItemModel(photo.id, photo.title, photo.url, photo.thumbnailUrl)
        }
    }
}