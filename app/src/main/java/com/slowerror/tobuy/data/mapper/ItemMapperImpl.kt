package com.slowerror.tobuy.data.mapper

import com.slowerror.tobuy.data.local.entity.ItemEntity
import com.slowerror.tobuy.domain.model.Item

class ItemMapperImpl: Mapper<Item, ItemEntity> {
    override fun mapToData(input: Item): ItemEntity =
        ItemEntity(
            input.id,
            input.title,
            input.description,
            input.priority,
            input.createdAt,
            input.categoryId
        )


    override fun mapToDomain(input: ItemEntity): Item =
        Item(
            input.id,
            input.title,
            input.description,
            input.priority,
            input.createdAt,
            input.categoryId
        )

}