package com.slowerror.tobuy.data.repository

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.ItemMapperImpl
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToBuyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val itemMapper: ItemMapperImpl
) : ToBuyRepository {

    override fun getAllItem(): Flow<List<Item>> =
        appDatabase.itemDao().getAllItemEntities()
            .flowOn(Dispatchers.IO)
            .map { list ->
                list.map { itemMapper.mapToDomain(it) }
            }


    override suspend fun insertItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().insert(itemMapper.mapToData(item))
    }


    override suspend fun deleteItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().delete(itemMapper.mapToData(item))
    }

    override suspend fun updateItem(item: Item) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().updateItemEntity(itemMapper.mapToData(item))
    }

}