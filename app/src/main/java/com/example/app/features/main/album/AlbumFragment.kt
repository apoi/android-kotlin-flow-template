package com.example.app.features.main.album

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.data.state.State
import com.example.app.databinding.AlbumFragmentBinding
import com.example.app.features.main.MainActivity
import com.example.app.features.main.album.adapter.AlbumItemModel
import com.example.app.features.main.album.adapter.AlbumTypeFactory
import com.example.app.features.main.details.DetailsFragment
import com.example.app.ui.DividerDecoration
import com.example.app.ui.adapter.ListAdapter
import com.example.app.ui.base.BaseFragment
import com.example.app.ui.listener.setClickListener
import com.example.app.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class AlbumFragment : BaseFragment(R.layout.album_fragment) {

    private val viewModel: AlbumViewModel by inject()
    private val binding by viewBinding(AlbumFragmentBinding::bind)
    private val photoAdapter = ListAdapter<AlbumItemModel>(AlbumTypeFactory())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aboutRecyclerView.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)

            setHasFixedSize(true)
            addItemDecoration(DividerDecoration(context))

            setClickListener(::onItemSelected)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getPhotos()
                .collect {
                    when (it) {
                        State.Loading -> Unit
                        is State.Success -> { photoAdapter.setItems(it.value) }
                        is State.Error -> Unit
                    }
                }
        }

        binding.listFab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    private fun onItemSelected(photo: AlbumItemModel) {
        findNavController()
            .navigate(
                R.id.list_to_photo,
                bundleOf(
                    DetailsFragment.ID to photo.url,
                    MainActivity.FULLSCREEN to true
                )
            )
    }
}
