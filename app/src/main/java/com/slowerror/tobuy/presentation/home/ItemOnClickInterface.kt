package com.slowerror.tobuy.presentation.home

import com.slowerror.tobuy.domain.model.Item

interface ItemOnClickInterface {

    fun onDeleteItem(item: Item)

    fun onBumpPriority(item: Item)
}