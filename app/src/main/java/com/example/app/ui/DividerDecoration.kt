package com.example.app.ui

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R

class DividerDecoration(context: Context) : DividerItemDecoration(context, LinearLayoutManager.VERTICAL) {

    init {
        setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
    }
}
