package com.example.app.model.idlist.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.app.data.store.Merger
import com.example.app.data.store.StoreCore
import com.example.app.model.idlist.IdList

@Entity(tableName = "lists")
@TypeConverters(IntListConverters::class)
class IdListEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "values") val values: List<Int>
) {

    fun toIdList(): IdList {
        return IdList(id, values)
    }

    companion object {
        const val SEPARATOR = ","

        val merger: Merger<IdListEntity> = StoreCore.takeNew()

        fun fromIdList(idList: IdList): IdListEntity {
            return IdListEntity(idList.key, idList.values)
        }
    }
}
