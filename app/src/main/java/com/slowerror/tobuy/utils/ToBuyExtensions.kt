package com.slowerror.tobuy.utils

import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.google.android.material.color.MaterialColors
import com.slowerror.tobuy.presentation.base.BaseFragment
import com.slowerror.tobuy.presentation.epoxy.ViewBindingKotlinModel
import com.slowerror.tobuy.presentation.epoxy.models.HeaderEpoxyModel

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}

@ColorInt
fun View.getAttrColor(attrResId: Int): Int =
    MaterialColors.getColor(this, attrResId)