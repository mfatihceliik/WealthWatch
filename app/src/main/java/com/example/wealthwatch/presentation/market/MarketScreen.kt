package com.example.wealthwatch.presentation.market

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.market.components.ExpandableMarketCategory
import com.example.wealthwatch.presentation.market.components.MarketRowSection
import com.example.wealthwatch.presentation.market.components.GlobalHighlights
import com.example.wealthwatch.presentation.market.components.MajorIndices
import com.example.wealthwatch.presentation.market.components.PortfolioSummaryCard
import com.example.wealthwatch.presentation.navigation.routes.Route
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.presentation.model.AssetUiModel

@Composable
fun MarketScreen(
    marketViewModel: MarketViewModel = hiltViewModel(),
    onNavigate: (Route) -> Unit
) {
    val uiState by marketViewModel.uiState.collectAsStateWithLifecycle()
    val navigateAssetDetail: (AssetUiModel) -> Unit = { asset ->
        onNavigate(Route.AssetDetail(asset.symbol, asset.type.code))
    }
    val spacing = AppTheme.spacing

    BaseScreen(
        state = uiState,
        onRetry = { marketViewModel.onRetry() },
        viewModel = marketViewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            item(key = "portfolio") {
                PortfolioSummaryCard(
                    formattedTotalBalance = uiState.portfolioBalance.toString(),
                    currency = uiState.currency,
                    onCurrencyChange = { marketViewModel.onEvent(MarketUiEvent.OnCurrencyChange(it)) }
                )
            }

            item(key = "global_highlights") {
                GlobalHighlights(
                    highlights = uiState.marketPulse,
                    onItemClick = navigateAssetDetail
                )
            }
            
            if (uiState.bistGainers.isNotEmpty()) {
                item(key = "major_indices") {
                    MajorIndices(
                        indices = uiState.bistGainers,
                        onItemClick = navigateAssetDetail
                    )
                }
            }

            // 1. US STOCKS GROUP
            item(key = "us_stock") {
                ExpandableMarketCategory(title = stringResource(R.string.category_us_stock)) {
                    MarketRowSection(
                        title = stringResource(R.string.section_gainers),
                        assets = uiState.usStockGainers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Gainer
                    )
                    MarketRowSection(
                        title = stringResource(R.string.section_losers),
                        assets = uiState.usStockLosers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Loser
                    )
                }
            }

            // 2. BIST (TR) STOCKS GROUP
            item(key = "bist") {
                ExpandableMarketCategory(title = stringResource(R.string.category_tr_stock)) {
                    MarketRowSection(
                        title = stringResource(R.string.section_gainers),
                        assets = uiState.bistGainers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Gainer
                    )
                    MarketRowSection(
                        title = stringResource(R.string.section_losers),
                        assets = uiState.bistLosers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Loser
                    )
                }
            }

            // 3. CRYPTO GROUP
            item(key = "crypto") {
                ExpandableMarketCategory(title = stringResource(R.string.category_crypto)) {
                    MarketRowSection(
                        title = stringResource(R.string.section_gainers),
                        assets = uiState.cryptoGainers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Gainer
                    )
                    MarketRowSection(
                        title = stringResource(R.string.section_losers),
                        assets = uiState.cryptoLosers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Loser
                    )
                }
            }

            // 4. COMMODITIES GROUP
            item(key = "commodity") {
                ExpandableMarketCategory(title = stringResource(R.string.category_commodity)) {
                    MarketRowSection(
                        title = stringResource(R.string.section_gainers),
                        assets = uiState.commodityGainers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Gainer
                    )
                    MarketRowSection(
                        title = stringResource(R.string.section_losers),
                        assets = uiState.commodityLosers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Loser
                    )
                }
            }

            // 5. EXCHANGE GROUP
            item(key = "exchange") {
                ExpandableMarketCategory(title = stringResource(R.string.category_exchange)) {
                    MarketRowSection(
                        title = stringResource(R.string.section_gainers),
                        assets = uiState.exchangeGainers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Gainer
                    )
                    MarketRowSection(
                        title = stringResource(R.string.section_losers),
                        assets = uiState.exchangeLosers,
                        onItemClick = navigateAssetDetail,
                        sectionType = SectionType.Loser
                    )
                }
            }
        }
    }
}
