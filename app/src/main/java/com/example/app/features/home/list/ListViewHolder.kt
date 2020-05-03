package com.example.app.features.home.list

import android.view.View
import com.example.app.ui.adapter.CommonViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class ListViewHolder(view: View) : CommonViewHolder<ListItem>(view) {

    override fun bind(item: ListItem) {
        Picasso.get()
            .load(item.thumbnailUrl)
            .fit()
            .centerCrop()
            .into(itemView.list_item_photo)

        itemView.list_item_title.text = "Photo id: ${item.id}"
        itemView.list_item_description.text = item.title.capitalize()
    }
}
