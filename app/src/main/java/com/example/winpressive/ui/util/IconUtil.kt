package com.example.winpressive.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

@Composable
fun rememberAppIconRequest(packageName: String, size: Int = 128): ImageRequest {
    val context = LocalContext.current
    return ImageRequest.Builder(context)
        .data(context.packageManager.getApplicationIcon(packageName))
        .size(size)
        .crossfade(true)
        .build()
}
