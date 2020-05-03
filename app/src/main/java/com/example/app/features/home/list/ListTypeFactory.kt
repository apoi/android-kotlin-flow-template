package com.example.app.features.home.list

import android.view.View
import com.example.app.R
import com.example.app.ui.adapter.CommonListItem
import com.example.app.ui.adapter.CommonTypeFactory
import com.example.app.ui.adapter.CommonViewHolder

class ListTypeFactory : CommonTypeFactory {

    override fun type(item: CommonListItem): Int {
        return when (item) {
            is ListItem -> R.layout.list_item
            else -> error("Invalid item")
        }
    }

    override fun createViewHolder(parent: View, type: Int): CommonViewHolder<*> {
        return when (type) {
            R.layout.list_item -> ListViewHolder(parent)
            else -> error("Invalid type")
        }
    }
}
