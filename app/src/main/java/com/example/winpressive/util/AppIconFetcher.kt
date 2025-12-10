package com.example.winpressive.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.Fetcher
import coil.key.Keyer
import coil.request.Options
import coil.request.ImageRequest
import com.example.winpressive.core.model.AppInfo

class AppIconFetcher(
    private val context: Context,
    private val packageName: String
) : Fetcher {
    override suspend fun fetch(): DrawableResult? {
        val pm = context.packageManager
        return try {
            val icon = pm.getApplicationIcon(packageName)
            DrawableResult(
                drawable = icon,
                isSampled = false,
                dataSource = DataSource.DISK
            )
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    class Factory(private val context: Context) : Fetcher.Factory<String> {
        override fun create(data: String, options: Options, imageLoader: ImageLoader): Fetcher {
            return AppIconFetcher(context, data)
        }
    }
}
