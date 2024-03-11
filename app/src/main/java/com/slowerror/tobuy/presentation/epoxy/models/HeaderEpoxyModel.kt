package com.slowerror.tobuy.presentation.epoxy.models

import com.slowerror.tobuy.R
import com.slowerror.tobuy.databinding.ModelHeaderItemBinding
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel

data class HeaderEpoxyModel(val text: String) :
    ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {
    override fun ModelHeaderItemBinding.bind() {
        headerText.text = text
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }

}