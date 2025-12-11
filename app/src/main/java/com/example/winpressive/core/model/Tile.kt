package com.example.winpressive.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class TileSize {
    Small,  // 1x1
    Medium, // 2x2
    Wide,   // 4x2
    Large   // 4x4 (optional, mostly for widgets)
}

@Serializable
enum class TileType {
    App,
    Folder,
    Widget,
    LiveClock,
    LiveWeather
}

@Serializable
data class Tile(
    val id: String, // Unique UUID
    val type: TileType,
    val size: TileSize,
    val rowIndex: Int,
    val colIndex: Int,

    // App specific
    val packageName: String? = null,
    val activityName: String? = null,
    val label: String? = null,

    // Widget specific
    val widgetId: Int? = null,

    // Folder specific
    val folderId: String? = null,

    // Live Tile Config
    val liveTileConfig: String? = null
)
