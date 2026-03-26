package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockDetail(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("assetId")
    val assetId: Int? = null,
    @SerialName("sector")
    val sector: String? = null,
    @SerialName("industry")
    val industry: String? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("peRatio")
    val peRatio: Double? = null,
    @SerialName("dividendYield")
    val dividendYield: Double? = null,
    @SerialName("earningsPerShare")
    val earningsPerShare: Double? = null
)
