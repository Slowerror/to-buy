package com.slowerror.tobuy.presentation.screens.customization

import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelCategoryBinding
import com.slowerror.tobuy.databinding.ModelEmptyButtonBinding
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.utils.addHeaderModel

class CustomizationController(
    private val categoryOnClickInterface: CategoryOnClickInterface
) : EpoxyController() {

    var categories: List<Category> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        addHeaderModel("Categories")

        categories.forEach { category ->
            CategoryEpoxyModel(category, categoryOnClickInterface).id(category.id).addTo(this)
        }

        EmptyButtonModel("Add category", categoryOnClickInterface)
            .id("add_category")
            .addTo(this)
    }

    data class CategoryEpoxyModel(
        private val category: Category,
        private val categoryOnClickInterface: CategoryOnClickInterface
    ) : ViewBindingKotlinModel<ModelCategoryBinding>(R.layout.model_category) {
        override fun ModelCategoryBinding.bind() {
            textView.text = category.name
            root.setOnLongClickListener {
                categoryOnClickInterface.removeCategory(category)
                true
            }
        }
    }

    data class EmptyButtonModel(
        val buttonText: String,
        private val categoryOnClickInterface: CategoryOnClickInterface
    ) : ViewBindingKotlinModel<ModelEmptyButtonBinding>(R.layout.model_empty_button) {
        override fun ModelEmptyButtonBinding.bind() {
            button.text = buttonText
            button.setOnClickListener { categoryOnClickInterface.onClickButtonToAddCategory() }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}