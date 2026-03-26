package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockModel(
    @SerialName("symbol") val symbol: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("price") val price: Double = 0.0,
    @SerialName("change") val change: Double = 0.0,
    @SerialName("volume") val volume: Double = 0.0,
    @SerialName("marketCap") val marketCap: Double = 0.0,
    @SerialName("sector") val sector: String = "",
    @SerialName("iconUrl") val iconUrl: String = ""
)
