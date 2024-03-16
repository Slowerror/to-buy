package com.slowerror.tobuy.presentation.screens.profile

import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelCategoryBinding
import com.slowerror.tobuy.databinding.ModelEmptyButtonBinding
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.utils.addHeaderModel

class ProfileController(private val onCategoryEmptyStateClicked: () -> Unit) : EpoxyController() {

    var categories: List<Category> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        addHeaderModel("Categories")

        categories.forEach { category ->
            CategoryEpoxyModel(category).id(category.id).addTo(this)
        }

        EmptyButtonModel("Add category", onCategoryEmptyStateClicked)
            .id("add_category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        val category: Category
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {
        override fun ModelCategoryBinding.bind() {
            textView.text = category.name
        }
    }

    data class EmptyButtonModel(
        val buttonText: String,
        val onClicked: () -> Unit
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {
        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { onClicked.invoke() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}