package com.example.app.model.idlist.store

import androidx.room.TypeConverter

class IntListConverters {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(IdListEntity.SEPARATOR).map(String::toInt)
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        return list.joinToString(IdListEntity.SEPARATOR)
    }
}
