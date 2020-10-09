package com.example.app.feature.album.adapter

import android.view.ViewGroup
import com.example.app.R
import com.example.app.databinding.AlbumListItemBinding
import com.example.app.ui.adapter.ListItem
import com.example.app.ui.adapter.ListTypeFactory
import com.example.app.ui.adapter.ListViewHolder

class AlbumTypeFactory : ListTypeFactory() {

    override fun type(item: ListItem): Int {
        return when (item) {
            is AlbumItemModel -> R.layout.album_list_item
            else -> error("Invalid item")
        }
    }

    override fun createViewHolder(type: Int, parent: ViewGroup): ListViewHolder<*> {
        return when (type) {
            R.layout.album_list_item -> {
                AlbumViewHolder(createBinding(AlbumListItemBinding::inflate, parent))
            }
            else -> error("Invalid type")
        }
    }
}
