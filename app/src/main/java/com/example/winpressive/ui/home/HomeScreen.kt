package com.example.winpressive.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.winpressive.ui.components.TileView
import com.example.winpressive.util.launchApp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tiles by viewModel.tiles.collectAsState()
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(4), // Base grid unit
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(tiles, key = { it.id }, span = { tile ->
            GridItemSpan(tile.size.span)
        }) { tile ->
            TileView(
                tile = tile,
                modifier = Modifier.fillMaxSize(), // Height will be determined by aspect ratio logic in a real staggered grid
                onClick = {
                     if (tile.type == com.example.winpressive.core.model.TileType.App) {
                         tile.appInfo?.let { context.launchApp(it) }
                     }
                },
                onLongClick = { /* Start Drag and Drop */ }
            )
        }
    }
}
