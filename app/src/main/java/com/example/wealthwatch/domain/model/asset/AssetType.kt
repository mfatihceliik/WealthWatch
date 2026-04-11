package com.example.wealthwatch.domain.model.asset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetType(
    @SerialName("id")
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String = ""
) {
    val code: String get() = name

    companion object {
        val US_STOCK = AssetType(1, "US_STOCK", "US Stocks (NASDAQ, NYSE, etc.)")
        val BIST = AssetType(2, "BIST", "Borsa Istanbul Stocks")
        val CRYPTO = AssetType(3, "CRYPTO", "Cryptocurrencies")
        val COMMODITY = AssetType(4, "COMMODITY", "Commodities (Gold, Oil, etc.)")
        val EXCHANGE = AssetType(5, "CURRENCIES", "Foreign Exchange (Currencies)")
        val OTHER = AssetType(-1, "other", "Unknown Asset Type")

        val entries = listOf(CRYPTO, US_STOCK, BIST, EXCHANGE, COMMODITY, OTHER)

        fun fromCode(code: String): AssetType {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: OTHER
        }
    }
}
