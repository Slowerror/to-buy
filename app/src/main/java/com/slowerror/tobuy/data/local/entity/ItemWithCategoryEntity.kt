package com.slowerror.tobuy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithCategoryEntity(
    @Embedded val item: ItemEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )

    val category: CategoryEntity?
)
