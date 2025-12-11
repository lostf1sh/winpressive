package com.example.winpressive.data.launcher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.example.winpressive.core.model.AppInfo
import com.example.winpressive.core.model.Tile
import com.example.winpressive.core.model.TileSize
import com.example.winpressive.core.model.TileType
import com.example.winpressive.data.launcher.local.TileDao
import com.example.winpressive.data.launcher.local.TileEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LauncherRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tileDao: TileDao
) {
    suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfos = pm.queryIntentActivities(intent, 0)
        resolveInfos.mapNotNull {
            try {
                AppInfo(
                    packageName = it.activityInfo.packageName,
                    label = it.loadLabel(pm).toString(),
                    activityName = it.activityInfo.name
                )
            } catch (e: Exception) {
                null
            }
        }.sortedBy { it.label }
    }

    val homeTiles: Flow<List<Tile>> = tileDao.getAllTiles().map { entities ->
        entities.map { it.toDomain() }.sortedWith(compareBy({it.rowIndex}, {it.colIndex}))
    }

    suspend fun saveTiles(tiles: List<Tile>) = withContext(Dispatchers.IO) {
        tileDao.clearAll()
        tileDao.insertAll(tiles.map { it.toEntity() })
    }

    suspend fun addTile(app: AppInfo) = withContext(Dispatchers.IO) {
        // Logic to add tile would go here
    }

    suspend fun insertOrUpdateTile(tile: Tile) = withContext(Dispatchers.IO) {
        tileDao.insertOrUpdate(tile.toEntity())
    }

    suspend fun removeTile(tileId: String) = withContext(Dispatchers.IO) {
        tileDao.deleteById(tileId)
    }

    // Mappers
    private fun TileEntity.toDomain(): Tile = Tile(
        id = id,
        type = type,
        size = size,
        rowIndex = rowIndex,
        colIndex = colIndex,
        packageName = packageName,
        activityName = activityName,
        label = label,
        widgetId = widgetId,
        folderId = folderId,
        liveTileConfig = liveTileConfig
    )

    private fun Tile.toEntity(): TileEntity = TileEntity(
        id = id,
        type = type,
        size = size,
        rowIndex = rowIndex,
        colIndex = colIndex,
        packageName = packageName,
        activityName = activityName,
        label = label,
        widgetId = widgetId,
        folderId = folderId,
        liveTileConfig = liveTileConfig
    )
}
