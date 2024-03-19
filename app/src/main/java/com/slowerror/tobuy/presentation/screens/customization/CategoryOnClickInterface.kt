package com.slowerror.tobuy.presentation.screens.customization

import com.slowerror.tobuy.domain.model.Category

interface CategoryOnClickInterface {
    fun removeCategory(category: Category)
    fun onClickButtonToAddCategory()
    fun onPrioritySelected(priorityName: String)
}