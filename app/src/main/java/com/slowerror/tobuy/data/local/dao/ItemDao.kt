package com.slowerror.tobuy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.slowerror.tobuy.data.local.entity.ItemEntity

@Dao
interface ItemDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): List<ItemEntity>

    @Insert
    fun insert(itemEntity: ItemEntity)

    @Delete
    fun delete(itemEntity: ItemEntity)
}