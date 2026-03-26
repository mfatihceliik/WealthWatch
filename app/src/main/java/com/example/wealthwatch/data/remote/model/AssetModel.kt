package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetModel(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("iconUrl")
    val iconUrl: String? = null,
    @SerialName("assetTypeId")
    val assetTypeId: Int? = null,
    @SerialName("assetType")
    val assetType: AssetType? = null,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("priceChange")
    val priceChange: Double? = null,
    @SerialName("changePercent")
    val changePercent: Double? = null,
    @SerialName("volume")
    val volume: Double? = null,
    @SerialName("marketCap")
    val marketCap: Double? = null,
    @SerialName("high")
    val high: Double? = null,
    @SerialName("low")
    val low: Double? = null,
    @SerialName("open")
    val open: Double? = null,
    @SerialName("lastUpdated")
    val lastUpdated: String,
    @SerialName("stockDetail")
    val stockDetail: StockDetail? = null,
    @SerialName("cryptoDetail")
    val cryptoDetail: CryptoDetail? = null,
    @SerialName("exchangeDetail")
    val exchangeDetail: ExchangeDetail? = null
)
