package com.slowerror.tobuy.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val priority: Int = 0,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = 0L,
    @ColumnInfo(name = "category_id")
    val categoryId: String = ""
)