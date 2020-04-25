package com.example.app.features.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.app.R
import com.example.app.ui.adapter.CommonViewHolder
import com.squareup.picasso.Picasso

class PhotoViewHolder(view: View) : CommonViewHolder<PhotoItem>(view) {

    private val title = view.findViewById<TextView>(R.id.item_title)
    private val description = view.findViewById<TextView>(R.id.item_description)
    private val photoView = view.findViewById<ImageView>(R.id.item_photo)

    override fun bind(item: PhotoItem) {
        Picasso.get()
            .load(item.thumbnailUrl)
            .fit()
            .centerCrop()
            .into(photoView)

        title.text = "Photo id: ${item.id}"
        description.text = item.title.capitalize()
    }
}
