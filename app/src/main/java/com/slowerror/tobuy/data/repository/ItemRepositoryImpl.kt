package com.slowerror.tobuy.data.repository

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.CategoryMapperImpl
import com.slowerror.tobuy.data.mapper.ItemMapperImpl
import com.slowerror.tobuy.domain.model.Category
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.model.ItemWithCategory
import com.slowerror.tobuy.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ItemRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val itemMapper: ItemMapperImpl,
    private val categoryMapper: CategoryMapperImpl
) : ItemRepository {

    override fun getAllItem(): Flow<List<Item>> =
        appDatabase.itemDao().getAllItemEntities()
            .flowOn(Dispatchers.IO)
            .map { list ->
                list.map { itemMapper.mapToDomain(it) }
            }

    override fun getAllItemWithCategory(): Flow<List<ItemWithCategory>> {
        return appDatabase.itemDao().getItemWithCategoryEntities()
            .flowOn(Dispatchers.IO)
            .map {
                it.map { entry ->
                    ItemWithCategory(
                        itemMapper.mapToDomain(entry.key),
                        entry.value?.let { categoryEntity ->
                            categoryMapper.mapToDomain(categoryEntity)
                        } ?: Category.getDefaultCategory()
                    )
                }
            }
    }

    override suspend fun insertItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().insert(itemMapper.mapToData(item))
    }


    override suspend fun deleteItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().delete(itemMapper.mapToData(item))
    }

    override suspend fun updateItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().update(itemMapper.mapToData(item))
    }

}