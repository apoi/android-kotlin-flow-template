package com.example.app.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class CommonViewHolder<T : CommonListItem>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}
