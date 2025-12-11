package com.example.winpressive.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.winpressive.core.model.TileSize
import com.example.winpressive.ui.util.rememberAppIconRequest

@Composable
fun WinTile(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
fun AppTile(
    modifier: Modifier = Modifier,
    label: String,
    packageName: String,
    tileSize: TileSize,
    onClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = rememberAppIconRequest(packageName)
    )

    WinTile(
        modifier = modifier.fillMaxSize(),
        onClick = onClick
    ) {
        // Layout depends on size
        when (tileSize) {
            TileSize.Small -> {
                // Just icon centered? Or tiny icon + text? Small is usually 1x1.
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painter,
                        contentDescription = label,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            TileSize.Medium -> {
                // 2x2: Icon center or bottom-left text
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painter,
                        contentDescription = label,
                        modifier = Modifier.size(48.dp).align(Alignment.Center),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.BottomStart),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            TileSize.Wide -> {
                // 4x2
                Box(modifier = Modifier.fillMaxSize()) {
                     Image(
                        painter = painter,
                        contentDescription = label,
                        modifier = Modifier.size(48.dp).align(Alignment.CenterStart),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            TileSize.Large -> {
                 // 4x4
                 Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                      Image(
                        painter = painter,
                        contentDescription = label,
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = label, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                 }
            }
        }
    }
}
