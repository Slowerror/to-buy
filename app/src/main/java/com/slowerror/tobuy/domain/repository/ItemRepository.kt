package com.slowerror.tobuy.domain.repository

import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.model.ItemWithCategory
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getAllItem(): Flow<List<Item>>

    fun getAllItemWithCategory(): Flow<List<ItemWithCategory>>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)
}