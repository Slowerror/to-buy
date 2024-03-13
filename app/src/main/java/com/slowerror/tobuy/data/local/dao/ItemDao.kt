package com.slowerror.tobuy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.slowerror.tobuy.data.local.entity.CategoryEntity
import com.slowerror.tobuy.data.local.entity.ItemEntity
import com.slowerror.tobuy.data.local.entity.ItemWithCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): Flow<List<ItemEntity>>

    /*@Transaction
    @Query("SELECT * FROM item_entity")
    fun getItemWithCategoryEntities(): Flow<List<ItemWithCategoryEntity>>*/

    @Query("SELECT * FROM item_entity LEFT JOIN category_entity ON category_id = category_entity.id")
    fun getItemWithCategoryEntities(): Flow<Map<ItemEntity, CategoryEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)

    @Update
    suspend fun update(itemEntity: ItemEntity)
}