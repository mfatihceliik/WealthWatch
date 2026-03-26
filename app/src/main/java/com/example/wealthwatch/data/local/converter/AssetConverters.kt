package com.example.wealthwatch.data.local.converter

import androidx.room.TypeConverter
import com.example.wealthwatch.domain.model.asset.AssetType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AssetConverters {
    @TypeConverter 
    fun fromAssetType(type: AssetType): String {
        return Json.encodeToString(type)
    }
    
    @TypeConverter 
    fun toAssetType(name: String): AssetType {
        return try {
            Json.decodeFromString<AssetType>(name)
        } catch (e: Exception) {
            AssetType.OTHER
        }
    }

    @TypeConverter fun fromTimestamp(value: Long?) = value?.let { java.util.Date(it) }

    @TypeConverter fun dateToTimestamp(date: java.util.Date?) = date?.time
}
