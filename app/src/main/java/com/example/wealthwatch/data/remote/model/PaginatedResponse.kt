package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse<T>(
    @SerialName("data") val data: T,
    @SerialName("pagination") val pagination: PaginationInfo
)
