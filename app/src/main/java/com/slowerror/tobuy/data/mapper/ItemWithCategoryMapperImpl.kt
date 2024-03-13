package com.slowerror.tobuy.data.mapper

import com.slowerror.tobuy.data.local.entity.CategoryEntity
import com.slowerror.tobuy.data.local.entity.ItemWithCategoryEntity
import com.slowerror.tobuy.domain.model.ItemWithCategory

class ItemWithCategoryMapperImpl(
    private val itemMapper: ItemMapperImpl,
    private val categoryMapper: CategoryMapperImpl
) : Mapper<ItemWithCategory, ItemWithCategoryEntity> {
    override fun mapToData(input: ItemWithCategory): ItemWithCategoryEntity =
       ItemWithCategoryEntity(
           item = itemMapper.mapToData(input.item),
           category = categoryMapper.mapToData(input.category)
       )


    override fun mapToDomain(input: ItemWithCategoryEntity): ItemWithCategory =
        ItemWithCategory(
            item = itemMapper.mapToDomain(input.item),
            category = categoryMapper.mapToDomain(input.category ?: CategoryEntity("", ""))
        )
}