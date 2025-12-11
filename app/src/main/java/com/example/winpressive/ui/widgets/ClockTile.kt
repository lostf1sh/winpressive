package com.example.winpressive.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

@Composable
fun ClockTile(modifier: Modifier = Modifier) {
    var time by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while(true) {
            time = LocalDateTime.now()
            delay(1000)
        }
    }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = time.format(timeFormatter),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = time.format(dateFormatter),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}
