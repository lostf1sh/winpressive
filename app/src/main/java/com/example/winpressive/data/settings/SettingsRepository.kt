package com.example.winpressive.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val GRID_COLUMNS_KEY = intPreferencesKey("grid_columns")
    
    val gridColumns: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[GRID_COLUMNS_KEY] ?: 4 // Default to 4 columns
        }

    suspend fun setGridColumns(columns: Int) {
        context.dataStore.edit { preferences ->
            preferences[GRID_COLUMNS_KEY] = columns
        }
    }
}
