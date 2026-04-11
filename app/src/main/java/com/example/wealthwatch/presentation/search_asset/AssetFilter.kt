package com.example.wealthwatch.presentation.search_asset

import com.example.wealthwatch.domain.model.asset.AssetType

enum class AssetFilter(val label: String, val type: AssetType?) {
    ALL("All", null),
    STOCKS("Stocks (US)", AssetType.US_STOCK),
    BIST("BIST (TR)", AssetType.BIST),
    CRYPTO("Crypto", AssetType.CRYPTO),
    EXCHANGE("Currency", AssetType.EXCHANGE),
    COMMODITIES("Commodities", AssetType.COMMODITY),
}