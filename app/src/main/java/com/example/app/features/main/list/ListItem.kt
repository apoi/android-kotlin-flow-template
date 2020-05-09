package com.example.app.features.main.list

import com.example.app.data.pojo.Photo
import com.example.app.ui.adapter.CommonListItem
import com.example.app.ui.adapter.CommonTypeFactory

data class ListItem(
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : CommonListItem {

    override fun id(): Long {
        return id.toLong()
    }

    override fun type(factory: CommonTypeFactory): Int {
        return factory.type(this)
    }

    companion object {
        fun from(photo: Photo): ListItem {
            return ListItem(photo.id, photo.title, photo.url, photo.thumbnailUrl)
        }
    }
}
