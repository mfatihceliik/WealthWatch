package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName


import kotlinx.serialization.Serializable

@Serializable
data class MarketOverviewResponse(
    @SerialName("pulse")
    val pulse: List<MarketModel>,
    @SerialName("crypto")
    val crypto: MarketCategoryModel,
    @SerialName("us_stock")
    val usStock: MarketCategoryModel,
    @SerialName("tr_stock")
    val trStock: MarketCategoryModel,
    @SerialName("currency")
    val currency: MarketCategoryModel,
    @SerialName("commodity")
    val commodity: MarketCategoryModel
)

