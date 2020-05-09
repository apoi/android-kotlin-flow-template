package com.example.app.features.photo

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.databinding.PhotoActivityBinding
import com.example.app.util.viewBinding
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject

class PhotoActivity : AppCompatActivity(R.layout.photo_activity) {

    private val picasso: Picasso by inject()
    private val binding by viewBinding(PhotoActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN)
        initImageView(intent.getStringExtra(PHOTO) ?: "")
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
