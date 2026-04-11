package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResponse(
    @SerialName("base") val base: String,
    @SerialName("rates") val rates: Map<String, ExchangeRateDetail>
)


