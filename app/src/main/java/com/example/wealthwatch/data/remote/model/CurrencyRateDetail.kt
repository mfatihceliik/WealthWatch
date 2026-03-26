package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDetail(
    @SerialName("name") val name: String,
    @SerialName("type") val type: String = "",
    @SerialName("price") val price: Double,
    @SerialName("change") val change: Double,
    @SerialName("pctChange") val changePercent: Double,
    @SerialName("iconUrl") val iconUrl: String
)
