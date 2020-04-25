package com.example.app.features.photo

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.app.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_activity.*
import org.koin.android.ext.android.inject

const val PHOTO = "photo"

class PhotoActivity : Activity() {

    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.photo_activity)
        initImageView(intent.getStringExtra(PHOTO) ?: "")
    }

    private fun initImageView(source: String) {
        picasso.load(source)
            .centerInside()
            .fit()
            .into(photo_view)
    }
}
