package com.slowerror.tobuy.domain.repository

import com.slowerror.tobuy.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategory(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun updateCategory(category: Category)
}