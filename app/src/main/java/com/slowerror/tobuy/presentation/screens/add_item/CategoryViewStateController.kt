package com.slowerror.tobuy.presentation.screens.add_item

import android.content.res.ColorStateList
import android.graphics.Typeface
import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelCategoryItemSelectionBinding
import com.slowerror.tobuy.presentation.base.CategoriesViewState
import com.slowerror.tobuy.presentation.epoxy.LoadingEpoxyModel
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.utils.getAttrColor

class CategoryViewStateController(
    private val onCategorySelected: (String) -> Unit
) : EpoxyController() {

    var categoriesViewState = CategoriesViewState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if (categoriesViewState.isLoading) {
            LoadingEpoxyModel().id("Loading").addTo(this)
            return
        }

        categoriesViewState.categoryItemList.forEach {
            CategoryViewStateModel(it, onCategorySelected).id(it.category.id).addTo(this)
        }
    }


    data class CategoryViewStateModel(
        val categoryItem: CategoriesViewState.CategoryItem,
        private val onCategorySelected: (String) -> Unit
    ) : ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection) {
        override fun ModelCategoryItemSelectionBinding.bind() {

            nameCategoryTextView.text = categoryItem.category.name
            root.setOnClickListener { onCategorySelected(categoryItem.category.id) }

            val colorRes =
                if (categoryItem.isSelected)
                    com.google.android.material.R.attr.colorSecondary
                else
                    com.google.android.material.R.attr.colorPrimary

            val color = root.getAttrColor(colorRes)

            nameCategoryTextView.setTextColor(color)
            nameCategoryTextView.typeface =
                if (categoryItem.isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

            root.setStrokeColor(ColorStateList.valueOf(color))
        }

    }
}