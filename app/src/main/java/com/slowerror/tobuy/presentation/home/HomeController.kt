package com.slowerror.tobuy.presentation.home

import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelItemBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.epoxy.LoadingEpoxyModel
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel

class HomeController(
    private val itemOnClickInterface: ItemOnClickInterface
) : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var itemList = ArrayList<Item>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("Loading_state").addTo(this)
            return
        }

        if (itemList.isEmpty()) {
            return
        }

        itemList.forEach {item ->
            ItemEpoxyModel(item, itemOnClickInterface)
                .id(item.id)
                .addTo(this)
        }
    }

    data class ItemEpoxyModel(
        val item: Item,
        val itemOnClickInterface: ItemOnClickInterface
    ) : ViewBindingKotlinModel<ModelItemBinding>(R.layout.model_item) {

        override fun ModelItemBinding.bind() {
            titleTextView.text = item.title

            if (item.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = item.description
            }

            deleteImageView.setOnClickListener {
                itemOnClickInterface.onDeleteItem(item)
            }

            val color = when (item.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> android.R.color.background_dark
            }


            with(priorityTextView) {
                setBackgroundColor(ContextCompat.getColor(root.context, color))

                setOnClickListener {
                    itemOnClickInterface.onBumpPriority(item)
                }
            }

        }

    }

}