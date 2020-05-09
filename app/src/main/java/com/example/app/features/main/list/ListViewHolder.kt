package com.example.app.features.main.list

import com.example.app.databinding.ListItemBinding
import com.example.app.ui.adapter.CommonViewHolder
import com.squareup.picasso.Picasso

class ListViewHolder(
    private val binding: ListItemBinding
) : CommonViewHolder<ListItem>(binding.root) {

    override fun bind(item: ListItem) {
        Picasso.get()
            .load(item.thumbnailUrl)
            .fit()
            .centerCrop()
            .into(binding.listItemPhoto)

        binding.listItemTitle.text = "Photo id: ${item.id}"
        binding.listItemDescription.text = item.title.capitalize()
    }
}
