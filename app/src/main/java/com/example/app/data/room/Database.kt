package com.example.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app.domain.idlist.store.IdListDao
import com.example.app.domain.idlist.store.IdListEntity
import com.example.app.domain.photo.store.PhotoDao
import com.example.app.domain.photo.store.PhotoEntity

const val DATABASE_NAME = "app-database.db"
const val BATCH_SIZE = 999
private const val CURRENT_VERSION = 1

@Database(entities = [PhotoEntity::class, IdListEntity::class], version = CURRENT_VERSION)
abstract class Database : RoomDatabase() {

    abstract fun idListDao(): IdListDao

    abstract fun photoDao(): PhotoDao
}
