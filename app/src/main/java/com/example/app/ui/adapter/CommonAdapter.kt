package com.example.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CommonAdapter<T : CommonListItem>(
    private val typeFactory: CommonTypeFactory
) : RecyclerView.Adapter<CommonViewHolder<T>>() {

    init {
        setHasStableIds(true)
    }

    private val items: MutableList<T> = mutableListOf()

    fun setItems(newItems: List<T>) {
        if (items == newItems) return

        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun itemAt(position: Int): T {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return itemAt(position).id()
    }

    override fun getItemViewType(position: Int): Int {
        return itemAt(position).type(typeFactory)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return typeFactory.createViewHolder(view, viewType) as CommonViewHolder<T>
    }

    override fun onBindViewHolder(holder: CommonViewHolder<T>, position: Int) {
        holder.bind(itemAt(position))
    }
}
