package com.example.winpressive.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.winpressive.data.local.dao.TileDao
import com.example.winpressive.data.local.entity.TileEntity

@Database(entities = [TileEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tileDao(): TileDao
}
