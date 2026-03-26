package com.example.wealthwatch.domain.model.asset

import kotlinx.serialization.Serializable

@Serializable
data class AssetType(
    val id: Int = -1,
    val name: String = "",
    val description: String = ""
) {
    val code: String get() = name

    companion object {
        val CRYPTO = AssetType(1, "CRYPTO", "Cryptocurrencies")
        val US_STOCK = AssetType(2, "us-stock", "US Stock Market")
        val TR_STOCK = AssetType(3, "bist", "Turkish Stock Market")
        val CURRENCY = AssetType(4, "currency", "Fiat Currencies")
        val COMMODITY = AssetType(5, "COMMODITY", "Commodities")
        val OTHER = AssetType(-1, "other", "Unknown Asset Type")

        val entries = listOf(CRYPTO, US_STOCK, TR_STOCK, CURRENCY, COMMODITY, OTHER)

        fun fromCode(code: String): AssetType {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: OTHER
        }
    }
}
