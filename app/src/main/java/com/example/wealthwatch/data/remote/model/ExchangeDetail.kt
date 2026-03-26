package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeDetail(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("assetId")
    val assetId: Int? = null,
    @SerialName("baseCurrency")
    val baseCurrency: String? = null,
    @SerialName("quoteCurrency")
    val quoteCurrency: String? = null,
    @SerialName("region")
    val region: String? = null
)
