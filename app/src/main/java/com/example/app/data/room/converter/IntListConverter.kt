package com.example.app.data.room.converter

import androidx.room.TypeConverter

private const val SEPARATOR = ","

class IntListConverter {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(SEPARATOR).map(String::toInt)
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        return list.joinToString(SEPARATOR)
    }
}
