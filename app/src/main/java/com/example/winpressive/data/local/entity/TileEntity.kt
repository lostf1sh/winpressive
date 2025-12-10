package com.example.winpressive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.winpressive.core.model.AppInfo
import com.example.winpressive.core.model.Tile
import com.example.winpressive.core.model.TileSize
import com.example.winpressive.core.model.TileType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "tiles")
data class TileEntity(
    @PrimaryKey val id: String,
    val type: TileType,
    val size: TileSize,
    // Flattened AppInfo or use TypeConverter. We'll use TypeConverter for simplicity given @Serializable
    val appInfo: AppInfo? = null,
    val folderId: String? = null,
    val widgetId: Int? = null,
    val rowIndex: Int
)

fun TileEntity.toExternalModel(): Tile {
    return Tile(
        id = id,
        type = type,
        size = size,
        appInfo = appInfo,
        folderId = folderId,
        widgetId = widgetId,
        rowIndex = rowIndex
    )
}

fun Tile.toEntity(): TileEntity {
    return TileEntity(
        id = id,
        type = type,
        size = size,
        appInfo = appInfo,
        folderId = folderId,
        widgetId = widgetId,
        rowIndex = rowIndex
    )
}
