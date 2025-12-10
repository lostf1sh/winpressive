package com.example.winpressive.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.winpressive.core.model.AppInfo
import com.example.winpressive.core.model.Tile
import com.example.winpressive.core.model.TileSize
import com.example.winpressive.core.model.TileType
import com.example.winpressive.data.local.dao.TileDao
import com.example.winpressive.data.local.entity.toEntity
import com.example.winpressive.data.local.entity.toExternalModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LauncherRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tileDao: TileDao
) {
    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps = _installedApps.asStateFlow()
    
    // CoroutineScope for repository operations
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Backed by Room
    val tiles: StateFlow<List<Tile>> = tileDao.getTiles()
        .map { entities -> entities.map { it.toExternalModel() } }
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun addTile(app: AppInfo, size: TileSize = TileSize.Medium) {
        scope.launch {
            val currentTiles = tiles.first()
            val newIndex = currentTiles.size
            
            val newTile = Tile(
                id = UUID.randomUUID().toString(),
                type = TileType.App,
                size = size,
                appInfo = app,
                rowIndex = newIndex
            )
            tileDao.insertTile(newTile.toEntity())
        }
    }
    
    fun removeTile(tileId: String) {
        scope.launch {
            tileDao.deleteTile(tileId)
        }
    }

    fun moveTile(fromIndex: Int, toIndex: Int) {
        scope.launch {
            val currentTiles = tiles.first().toMutableList()
            if (fromIndex in currentTiles.indices && toIndex in currentTiles.indices) {
                val item = currentTiles.removeAt(fromIndex)
                currentTiles.add(toIndex, item)
                
                // Re-index all tiles
                val updatedTiles = currentTiles.mapIndexed { index, tile ->
                    tile.copy(rowIndex = index)
                }
                
                // Batch update (simple delete all and re-insert for now to ensure order)
                // In production, transaction with updates would be better
                tileDao.clearAll()
                tileDao.insertTiles(updatedTiles.map { it.toEntity() })
            }
        }
    }
    
    // Initial Default Tiles
    suspend fun ensureDefaultTiles() {
        if (tiles.first().isEmpty()) {
            val clockTile = Tile(UUID.randomUUID().toString(), TileType.Clock, TileSize.Wide, rowIndex = 0)
            val weatherTile = Tile(UUID.randomUUID().toString(), TileType.Weather, TileSize.Small, rowIndex = 1)
            tileDao.insertTiles(listOf(clockTile.toEntity(), weatherTile.toEntity()))
        }
    }
}
