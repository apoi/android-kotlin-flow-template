package com.example.app.features.main.photo

import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.PhotoFragmentBinding
import com.example.app.util.viewLifecycle
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class PhotoFragment : Fragment(R.layout.photo_fragment) {

    private val picasso: Picasso by inject()
    private val binding by viewLifecycle { PhotoFragmentBinding.bind(requireView()) }

    override fun onResume() {
        super.onResume()
        initImageView(arguments?.getString(PHOTO) ?: "")
    }

    private fun initImageView(source: String) {
        picasso.load(source)
            .centerInside()
            .fit()
            .into(binding.photoView)
    }

    companion object {
        const val PHOTO = "photo"
    }
}
