package com.example.app.feature.album.simple

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.data.state.State
import com.example.app.databinding.AlbumFragmentBinding
import com.example.app.feature.album.simple.adapter.AlbumItemModel
import com.example.app.feature.album.simple.adapter.AlbumTypeFactory
import com.example.app.navigation.NavParams
import com.example.app.ui.DividerDecoration
import com.example.app.ui.adapter.simple.ListAdapter
import com.example.app.ui.base.BaseFragment
import com.example.app.ui.listener.setClickListener
import com.example.app.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class AlbumFragment : BaseFragment(R.layout.album_fragment) {

    private val binding by viewBinding(AlbumFragmentBinding::bind)
    private val photoAdapter = ListAdapter<AlbumItemModel>(AlbumTypeFactory())

    private val viewModel: AlbumViewModel by inject()

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
                is State.Error -> Unit
            }
        }
    }

    private fun onItemSelected(photo: AlbumItemModel) {
        findNavController()
            .navigate(
                R.id.simple_album_to_details,
                bundleOf(
                    NavParams.ID to photo.id,
                    NavParams.FULLSCREEN to true
                )
            )
    }
}
