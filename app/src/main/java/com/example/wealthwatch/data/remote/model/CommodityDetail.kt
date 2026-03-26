package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommodityDetail(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("assetId")
    val assetId: Int? = null
)
