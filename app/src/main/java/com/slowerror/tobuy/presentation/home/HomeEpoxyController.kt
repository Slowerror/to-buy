package com.slowerror.tobuy.presentation.home

import com.airbnb.epoxy.EpoxyController
import com.slowerror.tobuy.domain.model.Item

class HomeEpoxyController : EpoxyController() {

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

        }
    }
}