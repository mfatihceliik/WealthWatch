package com.example.wealthwatch.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationInfo(
    @SerialName("totalItems") val totalItems: Int,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("currentPage") val currentPage: Int,
    @SerialName("limit") val limit: Int
)
