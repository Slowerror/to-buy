package com.slowerror.tobuy.presentation.home

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelItemBinding
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
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
            return
        }

        if (itemList.isEmpty()) {
            return
        }

        itemList.forEach {item ->
            ItemEpoxyModel(item, itemOnClickInterface).id(item.id).addTo(this)
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

            priorityTextView.setOnClickListener {
                itemOnClickInterface.onBumpPriority(item)
            }
        }

    }

}