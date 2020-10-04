package com.example.app.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ListViewHolder<T : ListItem>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}
