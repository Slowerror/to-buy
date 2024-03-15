package com.slowerror.tobuy.presentation.base

import com.slowerror.tobuy.domain.model.Category

data class CategoriesViewState(
    val isLoading: Boolean = false,
    val categoryItemList: List<CategoryItem> = emptyList()
) {
    data class CategoryItem(
        val category: Category = Category.getDefaultCategory(),
        val isSelected: Boolean = false
    )

    fun getSelectedCategoryId() : String =
        categoryItemList.find { it.isSelected }?.category?.id ?: Category.DEFAULT_CATEGORY_ID
}