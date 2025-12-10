package com.example.winpressive.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.winpressive.core.model.AppInfo
import com.example.winpressive.core.model.Tile
import com.example.winpressive.core.model.TileSize
import com.example.winpressive.core.model.TileType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LauncherRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps = _installedApps.asStateFlow()

    // In a real app, this would be backed by Room or DataStore
    private val _tiles = MutableStateFlow<List<Tile>>(emptyList())
    val tiles = _tiles.asStateFlow()

    suspend fun loadInstalledApps() = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        
        val apps = pm.queryIntentActivities(intent, 0).map { resolveInfo ->
            AppInfo(
                label = resolveInfo.loadLabel(pm).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                activityName = resolveInfo.activityInfo.name
            )
        }.sortedBy { it.label }
        
        _installedApps.value = apps
    }

    // Simplified persistence simulation
    fun addTile(app: AppInfo, size: TileSize = TileSize.Medium) {
        val newTile = Tile(
            id = UUID.randomUUID().toString(),
            type = TileType.App,
            size = size,
            appInfo = app
        )
        _tiles.value = _tiles.value + newTile
    }
    
    fun removeTile(tileId: String) {
        _tiles.value = _tiles.value.filter { it.id != tileId }
    }

    fun moveTile(fromIndex: Int, toIndex: Int) {
        val mutableList = _tiles.value.toMutableList()
        val item = mutableList.removeAt(fromIndex)
        mutableList.add(toIndex, item)
        _tiles.value = mutableList
    }
    
    // Initial Default Tiles
    init {
        val clockTile = Tile(UUID.randomUUID().toString(), TileType.Clock, TileSize.Wide)
        val weatherTile = Tile(UUID.randomUUID().toString(), TileType.Weather, TileSize.Small)
        _tiles.value = listOf(clockTile, weatherTile)
    }
}
