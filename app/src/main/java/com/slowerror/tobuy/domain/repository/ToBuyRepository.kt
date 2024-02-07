package com.slowerror.tobuy.domain.repository

import com.slowerror.tobuy.domain.model.Item

interface ToBuyRepository {

    fun getAllItem(): List<Item>

    fun insertItem(item: Item)

    fun deleteItem(item: Item)
}