package com.example.winpressive.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ThemeSettings(
    val useDynamicColor: Boolean = true,
    val useWallpaperTiles: Boolean = false,
    val accentColor: Long = 0xFF00ADEF, // Default WinBlue
    val tileCornerRadius: Int = 8, // Default Medium
    val homeColumns: Int = 4, // Standard 4 columns (Windows Phone usually had 2 or 3 wide columns, 4 is flexible)
    val isDarkTheme: Boolean = true, // Default to Dark for OLED goodness
    val iconPack: String? = null // Future proof
)
