package com.example.app.features.list

import android.view.View
import com.example.app.R
import com.example.app.ui.adapter.CommonListItem
import com.example.app.ui.adapter.CommonTypeFactory
import com.example.app.ui.adapter.CommonViewHolder

class PhotoTypeFactory : CommonTypeFactory {

    @Suppress("USELESS_IS_CHECK")
    override fun type(item: CommonListItem): Int {
        return when (item) {
            is CommonListItem -> R.layout.list_item
            else -> error("Invalid item")
        }
    }

    override fun createViewHolder(parent: View, type: Int): CommonViewHolder<*> {
        return when (type) {
            R.layout.list_item -> PhotoViewHolder(parent)
            else -> error("Invalid type")
        }
    }
}
