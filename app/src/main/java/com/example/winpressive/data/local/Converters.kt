package com.example.winpressive.data.local

import androidx.room.TypeConverter
import com.example.winpressive.core.model.AppInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromAppInfo(value: AppInfo?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toAppInfo(value: String?): AppInfo? {
        return value?.let { Json.decodeFromString(it) }
    }
}
