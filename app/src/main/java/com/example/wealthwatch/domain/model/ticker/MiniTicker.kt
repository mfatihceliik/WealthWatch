package com.example.wealthwatch.domain.model.ticker

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MiniTicker(
    @SerialName("s") val symbol: String,
    @SerialName("c") val closePrice: String,
    @SerialName("o") val openPrice: String,
    @SerialName("h") val highPrice: String,
    @SerialName("l") val lowPrice: String,
    @SerialName("v") val volume: String,
    @SerialName("e") val eventType: String? = null,
    @SerialName("E") val eventTime: Long,
    @SerialName("q") val quoteVolume: String,
    val iconUrl: String = ""
)
