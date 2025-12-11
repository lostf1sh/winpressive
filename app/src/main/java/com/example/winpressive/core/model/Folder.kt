package com.example.winpressive.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Folder(
    val id: String,
    val label: String,
    val items: List<Tile> = emptyList()
)
