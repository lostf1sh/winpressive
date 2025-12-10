package com.example.winpressive.core.model

import kotlinx.serialization.Serializable

enum class TileSize(val span: Int) {
    Small(1), // 1x1
    Medium(2), // 2x2
    Wide(4)   // 4x2
}

enum class TileType {
    App, Folder, Widget, Clock, Weather
}

@Serializable
data class AppInfo(
    val label: String,
    val packageName: String,
    val activityName: String,
    val isSystem: Boolean = false
)

@Serializable
data class Tile(
    val id: String,
    val type: TileType,
    val size: TileSize,
    val appInfo: AppInfo? = null, // For App tiles
    val folderId: String? = null,
    val widgetId: Int? = null,
    val rowIndex: Int = 0 // For order persistence
)

data class Folder(
    val id: String,
    val name: String,
    val apps: List<AppInfo>
)
