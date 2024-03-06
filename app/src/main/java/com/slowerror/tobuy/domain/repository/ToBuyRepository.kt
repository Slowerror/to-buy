package com.slowerror.tobuy.domain.repository

import com.slowerror.tobuy.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ToBuyRepository {

    fun getAllItem(): Flow<List<Item>>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)
}