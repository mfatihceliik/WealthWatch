package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateResponse(
    @SerialName("base") val base: String,
    @SerialName("rates") val rates: Map<String, CurrencyRateDetail>
)


