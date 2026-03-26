package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*@Serializable
data class SearchInitialResponse(
    @SerialName("topMovers")
    val topMovers: GroupedAssetsDto,
    @SerialName("suggestedAssets")
    val suggestedAssets: GroupedAssetsDto
)*/

/*
@Serializable
data class GroupedAssetsDto(
    @SerialName("crypto")
    val crypto: List<AssetDto>? = null,
    @SerialName("us_stock")
    val usStock: List<AssetDto>? = null,
    @SerialName("tr_stock")
    val trStock: List<AssetDto>? = null,
    @SerialName("currency")
    val currency: List<AssetDto>? = null,
    @SerialName("commodity")
    val commodity: List<AssetDto>? = null
)
*/

/*
@Serializable
data class AssetDto(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double? = null,
    @SerialName("priceChangePercent")
    val priceChangePercent: Double? = null,
    @SerialName("type")
    val type: String,
    @SerialName("iconUrl")
    val iconUrl: String? = null
)
*/

@Serializable
data class SearchInitialResponse(
    @SerialName("topMovers")
    val topMovers: List<AssetModel>? = null,
    @SerialName("suggestedAssets")
    val suggestedAssets: List<AssetModel>? = null,
)
