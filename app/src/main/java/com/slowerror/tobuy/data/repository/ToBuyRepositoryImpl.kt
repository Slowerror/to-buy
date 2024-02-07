package com.slowerror.tobuy.data.repository

import com.slowerror.tobuy.data.local.AppDatabase
import com.slowerror.tobuy.data.mapper.ItemMapperImpl
import com.slowerror.tobuy.domain.model.Item
import com.slowerror.tobuy.domain.repository.ToBuyRepository

class ToBuyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val itemMapper: ItemMapperImpl
) : ToBuyRepository {

    override fun getAllItem(): List<Item> =
        appDatabase.itemDao().getAllItemEntities().map { itemEntity ->
            itemMapper.mapToDomain(itemEntity)
        }


    override fun insertItem(item: Item) =
        appDatabase.itemDao().insert(
            itemMapper.mapToData(item)
        )


    override fun deleteItem(item: Item) =
        appDatabase.itemDao().delete(
            itemMapper.mapToData(item)
        )

}