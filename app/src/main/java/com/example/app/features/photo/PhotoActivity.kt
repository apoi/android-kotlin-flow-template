package com.example.app.features.photo

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_activity.*
import org.koin.android.ext.android.inject

class PhotoActivity : AppCompatActivity(R.layout.photo_activity) {

    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN)
        initImageView(intent.getStringExtra(PHOTO) ?: "")
    }

    private fun initImageView(source: String) {
        picasso.load(source)
            .centerInside()
            .fit()
            .into(photo_view)
    }

    companion object {
        const val PHOTO = "photo"
    }
}
