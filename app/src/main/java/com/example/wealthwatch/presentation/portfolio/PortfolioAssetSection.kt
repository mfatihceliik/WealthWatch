package com.example.wealthwatch.presentation.portfolio

import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.model.AssetUiModel

data class PortfolioAssetSection(
    val type: AssetType,
    val assets: List<AssetUiModel>
)
