package com.example.app.feature.details

import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.databinding.DetailsFragmentBinding
import com.example.app.ui.base.BaseFragment
import com.example.app.util.viewBinding
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailsFragment : BaseFragment(R.layout.details_fragment) {

    private val picasso: Picasso by inject()
    private val binding by viewBinding(DetailsFragmentBinding::bind)

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel by inject<DetailsViewModel> { parametersOf(args.id) }

    override fun observeViewState() {
        observe(viewModel.photo) {
            loadImage(it.url)
        }
    }

    private fun loadImage(source: String) {
        picasso.load(source)
            .centerInside()
            .fit()
            .into(binding.photoView)
    }
}
