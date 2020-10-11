package com.example.app.model.photo

import android.os.Parcelable
import com.example.app.data.store.Merger
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: Int,
    val albumId: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable {

    companion object {
        val merger: Merger<Photo> = { _, new -> new }
    }
}
