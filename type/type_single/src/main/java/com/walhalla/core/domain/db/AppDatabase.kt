package com.walhalla.core.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.walhalla.core.domain.CategoryDao
import com.walhalla.core.domain.Constants
import com.walhalla.core.domain.entity.Category
import com.walhalla.core.domain.entity.Status

@Database(
    entities = [Category::class, Status::class],
    version = Constants.version,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun statusDao(): StatusDao
}
