package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketCategoryModel(
    @SerialName("gainers")
    val gainers: List<MarketModel> = emptyList(),
    @SerialName("losers")
    val losers: List<MarketModel> = emptyList()
)
