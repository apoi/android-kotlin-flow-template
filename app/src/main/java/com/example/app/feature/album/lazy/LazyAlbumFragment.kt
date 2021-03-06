package com.example.app.feature.album.lazy

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.data.state.State
import com.example.app.databinding.AlbumFragmentBinding
import com.example.app.feature.album.lazy.adapter.LazyAlbumItemModel
import com.example.app.feature.album.lazy.adapter.LazyAlbumTypeFactory
import com.example.app.ui.DividerDecoration
import com.example.app.ui.adapter.simple.ListAdapter
import com.example.app.ui.base.BaseFragment
import com.example.app.ui.listener.setClickListener
import com.example.app.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class LazyAlbumFragment : BaseFragment(R.layout.album_fragment) {

    private val binding by viewBinding(AlbumFragmentBinding::bind)
    private val photoAdapter = ListAdapter<LazyAlbumItemModel>(LazyAlbumTypeFactory())

    private val viewModel: LazyAlbumViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aboutRecyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)

            setHasFixedSize(true)
            addItemDecoration(DividerDecoration(context))

            setClickListener(::onItemSelected)
        }

        binding.listFab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun observeViewState() {
        observe(viewModel.photos) {
            when (it) {
                State.Loading -> Unit
                is State.Success -> photoAdapter.setItems(it.value)
                is State.Empty -> photoAdapter.setItems(emptyList())
                is State.Error -> Unit
            }
        }
    }

    private fun onItemSelected(photo: LazyAlbumItemModel) {
        val action = LazyAlbumFragmentDirections.toDetails(photo.id)
        findNavController().navigate(action)
    }
}
