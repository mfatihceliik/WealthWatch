package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.wealthwatch.domain.model.asset.AssetType
@Serializable
data class MarketModel(
    @SerialName("symbol") val symbol: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("assetType") val assetType: AssetType? = null,
    @SerialName("assetTypeId") val assetTypeId: Int? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("lastPrice") val lastPrice: Double? = null,
    @SerialName("priceChange") val priceChange: Double? = null,
    @SerialName("priceChangePercent") val priceChangePercent: Double? = null,
    @SerialName("changePercent") val changePercent: Double? = null,
    @SerialName("volume") val volume: Double? = null,
    @SerialName("marketCap") val marketCap: Double? = null,
    @SerialName("sector") val sector: String = "",
    @SerialName("iconUrl") val iconUrl: String = ""
)