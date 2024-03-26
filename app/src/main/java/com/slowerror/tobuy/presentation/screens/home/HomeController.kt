package com.slowerror.tobuy.presentation.screens.home

import android.content.res.ColorStateList
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelEmptyStateBinding
import com.slowerror.tobuy.databinding.ModelItemBinding
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.model.ItemWithCategory
import com.slowerror.tobuy.presentation.epoxy.LoadingEpoxyModel
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.utils.SharedPrefUtil
import com.slowerror.tobuy.utils.addHeaderModel

class HomeController(
    private val itemOnClickInterface: ItemOnClickInterface
) : EpoxyController() {

    var viewState = HomeViewState(isLoading = true)
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (viewState.isLoading) {
            LoadingEpoxyModel().id("Loading_state").addTo(this)
            return
        }

        if (viewState.dataItemList.isEmpty()) {
            EmptyStateItemEpoxyModel().id("Empty_state").addTo(this)
            return
        }

        viewState.dataItemList.forEach { item ->
            if (item.isHeader) {
                addHeaderModel(item.data as String)
                return@forEach
            }

            val itemWithCategory = item.data as ItemWithCategory

            ItemEpoxyModel(itemWithCategory, itemOnClickInterface)
                .id(itemWithCategory.item.id)
                .addTo(this)
        }

    }

    data class ItemEpoxyModel(
        val item: ItemWithCategory,
        val itemOnClickInterface: ItemOnClickInterface
    ) : ViewBindingKotlinModel<ModelItemBinding>(R.layout.model_item) {

        override fun ModelItemBinding.bind() {
            titleTextView.text = item.item.title

            if (item.item.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = item.item.description
            }

            categoryTextView.text =
                if (item.category.id == Category.DEFAULT_CATEGORY_ID) "" else item.category.name

            val color = when (item.item.priority) {
                1 -> SharedPrefUtil.getLowPriorityColor()
                2 -> SharedPrefUtil.getMediumPriorityColor()
                3 -> SharedPrefUtil.getHighPriorityColor()
                else -> com.google.android.material.R.color.material_blue_grey_800
            }


            with(priorityTextView) {
                setBackgroundColor(color)
                root.setStrokeColor(ColorStateList.valueOf(color))
                setOnClickListener {
                    itemOnClickInterface.onBumpPriority(item.item)
                }
            }

            root.setOnClickListener {
                itemOnClickInterface.onItemSelected(item.item)
            }
        }

    }

    class EmptyStateItemEpoxyModel :
        ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {}
    }


}