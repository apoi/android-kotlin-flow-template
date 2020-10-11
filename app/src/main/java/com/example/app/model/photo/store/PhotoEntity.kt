package com.example.app.model.photo.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app.data.store.Merger
import com.example.app.model.photo.Photo

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "album_id") val albumId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String
) {

    fun toPhoto(): Photo {
        return Photo(id, albumId, title, url, thumbnailUrl)
    }

    companion object {
        val merger: Merger<PhotoEntity> = { old, new ->
            fromPhoto(Photo.merger(old?.toPhoto(), new.toPhoto()))
        }

        fun fromPhoto(photo: Photo): PhotoEntity {
            return PhotoEntity(photo.id, photo.albumId, photo.title, photo.url, photo.thumbnailUrl)
        }
    }
}
