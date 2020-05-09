package com.example.app.features.main.list

import android.view.ViewGroup
import com.example.app.R
import com.example.app.databinding.ListItemBinding
import com.example.app.ui.adapter.CommonListItem
import com.example.app.ui.adapter.CommonTypeFactory
import com.example.app.ui.adapter.CommonViewHolder

class ListTypeFactory : CommonTypeFactory() {

    override fun type(item: CommonListItem): Int {
        return when (item) {
            is ListItem -> R.layout.list_item
            else -> error("Invalid item")
        }
    }

    override fun createViewHolder(type: Int, parent: ViewGroup): CommonViewHolder<*> {
        return when (type) {
            R.layout.list_item -> ListViewHolder(createBinding(ListItemBinding::inflate, parent))
            else -> error("Invalid type")
        }
    }
}
