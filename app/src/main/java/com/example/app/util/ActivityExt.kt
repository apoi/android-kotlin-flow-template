package com.example.app.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Lazy ViewBinding creator for Activities that use the alternative
 * constructor with layout passed in arguments.
 */
fun <T : ViewBinding> Activity.viewBinding(bind: (View) -> T): Lazy<T> = object : Lazy<T> {

    private var binding: T? = null

    override fun isInitialized(): Boolean = binding != null

    override val value: T
        get() = binding ?: bind(contentView()).also {
            binding = it
        }

    private fun contentView(): View {
        return findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    }
}