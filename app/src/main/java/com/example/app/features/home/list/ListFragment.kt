package com.example.app.features.home.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.features.photo.PHOTO
import com.example.app.features.photo.PhotoActivity
import com.example.app.ui.DividerDecoration
import com.example.app.ui.adapter.CommonAdapter
import com.example.app.ui.listener.setClickListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.android.ext.android.inject

class ListFragment : Fragment(R.layout.list_fragment) {

    private val viewModel: ListViewModel by inject()
    private val photoAdapter = CommonAdapter<PhotoItem>(PhotoTypeFactory())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        about_recycler_view.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(context)

            setHasFixedSize(true)
            addItemDecoration(DividerDecoration(context))

            setClickListener(::onItemSelected)
        }

        viewModel.getPhotos().observe(
            viewLifecycleOwner,
            Observer { photoAdapter.setItems(it) }
        )

        list_fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchPhotos()
    }

    private fun onItemSelected(photo: PhotoItem) {
        Intent(context, PhotoActivity::class.java)
            .apply { putExtra(PHOTO, photo.url) }
            .let { requireContext().startActivity(it) }
    }
}