package com.example.app.feature.album.lazy.adapter

import android.annotation.SuppressLint
import com.example.app.databinding.AlbumListItemBinding
import com.example.app.domain.photo.Photo
import com.example.app.ui.adapter.simple.ScopeListViewHolder
import com.squareup.picasso.Picasso
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LazyAlbumViewHolder(
    private val binding: AlbumListItemBinding
) : ScopeListViewHolder<LazyAlbumItemModel>(binding.root) {

    override fun bind(item: LazyAlbumItemModel, scope: CoroutineScope) {
        scope.launch {
            item.getPhoto().collect {
                withContext(Dispatchers.Main) { setPhoto(it) }
            }
        }
    }

    override fun unbind() {
        super.unbind()

        Picasso.get().cancelRequest(binding.listItemPhoto)
        binding.listItemPhoto.setImageDrawable(null)
        binding.listItemTitle.text = null
        binding.listItemDescription.text = null
    }

    @SuppressLint("SetTextI18n")
    private fun setPhoto(photo: Photo) {
        Picasso.get()
            .load(photo.thumbnailUrl)
            .fit()
            .centerCrop()
            .into(binding.listItemPhoto)

        binding.listItemTitle.text = "Photo id: ${photo.id}"
        binding.listItemDescription.text = photo.title.capitalize(Locale.ROOT)
    }
}
