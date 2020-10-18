package com.example.app.feature.album.simple.adapter

import com.example.app.databinding.AlbumListItemBinding
import com.example.app.ui.adapter.simple.ListViewHolder
import com.squareup.picasso.Picasso

class AlbumViewHolder(
    private val binding: AlbumListItemBinding
) : ListViewHolder<AlbumItemModel>(binding.root) {

    override fun bind(item: AlbumItemModel) {
        Picasso.get()
            .load(item.thumbnailUrl)
            .fit()
            .centerCrop()
            .into(binding.listItemPhoto)

        binding.listItemTitle.text = "Photo id: ${item.id}"
        binding.listItemDescription.text = item.title.capitalize()
    }
}
