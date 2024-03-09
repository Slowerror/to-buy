package com.slowerror.tobuy.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.slowerror.tobuy.data.local.dao.CategoryDao
import com.slowerror.tobuy.data.local.dao.ItemDao
import com.slowerror.tobuy.data.local.entity.CategoryEntity
import com.slowerror.tobuy.data.local.entity.ItemEntity

@Database(
    entities = [ItemEntity::class, CategoryEntity::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 2, to = 3, spec = AppDatabase.AutoMigrationFrom2To3::class)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao

    @RenameColumn(tableName = "item_entity", fromColumnName = "createdAt", toColumnName = "created_at")
    @RenameColumn(tableName = "item_entity", fromColumnName = "categoryId", toColumnName = "category_id")
    class AutoMigrationFrom2To3 : AutoMigrationSpec

    companion object {

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE `category_entity` (" +
                            "`id` TEXT NOT NULL," +
                            "`name` TEXT NOT NULL," +
                            "PRIMARY KEY(`id`)" +
                            ")"
                )
            }
        }

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "toBuy.db"
                    ).addMigrations(MIGRATION_1_2).build()
                }
                return instance
            }
        }

    }

}