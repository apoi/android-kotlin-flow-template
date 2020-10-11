package com.example.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.model.photo.store.PhotoDao
import com.example.app.model.photo.store.PhotoEntity

const val DATABASE_NAME = "app-database.db"
private const val CURRENT_VERSION = 1

@Database(entities = [PhotoEntity::class], version = CURRENT_VERSION)
abstract class Database : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}
