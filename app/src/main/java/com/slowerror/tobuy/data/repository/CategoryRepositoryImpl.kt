package com.slowerror.tobuy.data.repository

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.CategoryMapperImpl
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val categoryMapper: CategoryMapperImpl
) : CategoryRepository {

    override fun getAllCategory(): Flow<List<Category>> =
        appDatabase.categoryDao().getAllCategoryEntities()
            .flowOn(Dispatchers.IO)
            .map {list ->
                list.map { categoryMapper.mapToDomain(it) }
            }


    override suspend fun insertCategory(category: Category) = withContext(Dispatchers.IO) {
        appDatabase.categoryDao().insert(categoryMapper.mapToData(category))
    }

    override suspend fun deleteCategory(category: Category) = withContext(Dispatchers.IO) {
        appDatabase.categoryDao().delete(categoryMapper.mapToData(category))
    }

    override suspend fun updateCategory(category: Category) = withContext(Dispatchers.IO) {
        appDatabase.categoryDao().update(categoryMapper.mapToData(category))
    }

}