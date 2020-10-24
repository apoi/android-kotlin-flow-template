package com.example.app.domain.idlist.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.app.data.room.converter.IntListConverter
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import com.example.app.domain.idlist.IdList

@Entity(tableName = "lists")
@TypeConverters(IntListConverter::class)
class IdListEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "values") val values: List<Int>
) {

    fun toIdList(): IdList {
        return IdList(id, values)
    }

    companion object {
        val merger: Merger<IdListEntity> = StoreCore.takeNew()

        fun fromIdList(idList: IdList): IdListEntity {
            return IdListEntity(idList.key, idList.values)
        }
    }
}
