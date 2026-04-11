package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName


import kotlinx.serialization.Serializable

@Serializable
data class MarketOverviewResponse(
    @SerialName("pulse")
    val pulse: List<MarketModel> = emptyList(),
    @SerialName("crypto")
    val crypto: MarketCategoryModel = MarketCategoryModel(),
    @SerialName("us_stock")
    val usStock: MarketCategoryModel = MarketCategoryModel(),
    @SerialName("bist")
    val trStock: MarketCategoryModel = MarketCategoryModel(),
    @SerialName("exchange")
    val exchange: MarketCategoryModel = MarketCategoryModel(),
    @SerialName("commodity")
    val commodity: MarketCategoryModel = MarketCategoryModel()
)

