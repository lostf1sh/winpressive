package com.example.winpressive.util

import android.content.Context
import android.content.Intent
import com.example.winpressive.core.model.AppInfo

fun Context.launchApp(appInfo: AppInfo) {
    try {
        val intent = Intent().apply {
            setClassName(appInfo.packageName, appInfo.activityName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
