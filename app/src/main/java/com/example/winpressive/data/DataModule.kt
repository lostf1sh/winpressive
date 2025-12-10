package com.example.winpressive.data

import android.content.Context
import androidx.room.Room
import com.example.winpressive.data.local.AppDatabase
import com.example.winpressive.data.local.dao.TileDao
import com.example.winpressive.data.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "winpressive.db"
        ).build()
    }

    @Provides
    fun provideTileDao(database: AppDatabase): TileDao {
        return database.tileDao()
    }
    
    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
}
