package com.example.wealthwatch.data.local.converter

import androidx.room.TypeConverter
import com.example.wealthwatch.domain.model.asset.AssetType

class AssetTypeConverter {
    @TypeConverter
    fun fromAssetType(type: AssetType): String = type.code

    @TypeConverter
    fun toAssetType(name: String): AssetType = try {
        AssetType.fromCode(name)
    } catch (e: Exception) {
        AssetType.OTHER
    }
}
