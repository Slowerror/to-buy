package com.slowerror.tobuy.data.mapper

import com.slowerror.tobuy.data.local.entity.CategoryEntity
import com.slowerror.tobuy.domain.model.Category

class CategoryMapperImpl: Mapper<Category, CategoryEntity> {
    override fun mapToData(input: Category): CategoryEntity =
        CategoryEntity(
            input.id,
            input.name
        )


    override fun mapToDomain(input: CategoryEntity): Category =
        Category(
            input.id,
            input.name
        )

}