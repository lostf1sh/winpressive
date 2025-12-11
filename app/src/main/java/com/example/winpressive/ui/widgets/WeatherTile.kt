package com.example.winpressive.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherTile(modifier: Modifier = Modifier) {
    // Mock Weather
    Box(modifier = modifier.fillMaxSize()) {
         Column(modifier = Modifier.align(Alignment.Center)) {
             Icon(
                 imageVector = Icons.Default.WbSunny,
                 contentDescription = "Sunny",
                 modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
             )
             Text(
                 text = "72°",
                 style = MaterialTheme.typography.displaySmall
             )
         }
         Text(
             text = "Sunny",
             style = MaterialTheme.typography.bodyMedium,
             modifier = Modifier.align(Alignment.BottomStart)
         )
         Text(
            text = "Seattle",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.TopEnd)
         )
    }
}
