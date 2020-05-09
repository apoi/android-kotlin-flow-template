package com.example.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class CommonTypeFactory {

    abstract fun type(item: CommonListItem): Int

    abstract fun createViewHolder(type: Int, parent: ViewGroup): CommonViewHolder<*>

    protected fun <T : ViewBinding> createBinding(
        creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T,
        parent: ViewGroup
    ): T {
        return creator(LayoutInflater.from(parent.context), parent, false)
    }
}
