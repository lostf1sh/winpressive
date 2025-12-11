package com.example.winpressive.di

import android.content.Context
import androidx.room.Room
import com.example.winpressive.data.launcher.local.LauncherDatabase
import com.example.winpressive.data.launcher.local.TileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLauncherDatabase(@ApplicationContext context: Context): LauncherDatabase {
        return Room.databaseBuilder(
            context,
            LauncherDatabase::class.java,
            "launcher.db"
        ).build()
    }

    @Provides
    fun provideTileDao(database: LauncherDatabase): TileDao {
        return database.tileDao()
    }
}
