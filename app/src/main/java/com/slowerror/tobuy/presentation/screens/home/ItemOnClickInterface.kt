package com.slowerror.tobuy.presentation.screens.home

import com.slowerror.tobuy.domain.model.Item

interface ItemOnClickInterface {
    fun onBumpPriority(item: Item)
    fun onItemSelected(item: Item)
}