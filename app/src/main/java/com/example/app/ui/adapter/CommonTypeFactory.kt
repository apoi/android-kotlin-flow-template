package com.example.app.ui.adapter

import android.view.View

interface CommonTypeFactory {

    fun type(item: CommonListItem): Int

    fun createViewHolder(parent: View, type: Int): CommonViewHolder<*>
}
