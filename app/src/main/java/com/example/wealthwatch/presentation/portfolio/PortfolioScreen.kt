package com.example.wealthwatch.presentation.portfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wealthwatch.R
import com.example.wealthwatch.presentation.common.BaseScreen
import com.example.wealthwatch.presentation.components.AssetItem
import com.example.wealthwatch.presentation.components.WWText
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.components.PortfolioPieChart
import com.example.wealthwatch.presentation.market.components.PortfolioSummaryCard
import com.example.wealthwatch.presentation.portfolio.components.ExpandablePortfolioSection

@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel = hiltViewModel(),
    onNavigateToDetail: (String, AssetType) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = AppTheme.spacing

    BaseScreen(
        state = uiState,
        viewModel = viewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {

            item {
                // Total Balance Card with Currency Selector
                PortfolioSummaryCard(
                    formattedTotalBalance = uiState.totalBalance.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    currency = uiState.currency,
                    onCurrencyChange = { viewModel.onEvent(PortfolioUiEvent.OnCurrencyChange(it)) }
                )
            }

            item {
                // Pie Chart Frame
                PortfolioPieChart(
                    data = uiState.pieChartData,
                    formattedTotalBalance = uiState.formattedTotalBalance, // Pass formatted total
                    isChartGrouped = uiState.isPieChartGrouped,
                    onChartModeToggle = { viewModel.onEvent(PortfolioUiEvent.OnToggleChartMode) },
                    modifier = Modifier.height(250.dp) // Adjusted height slightly
                )
            }

            item {
                // Assets Header with Left Alignment
                Row(modifier = Modifier.fillMaxWidth()) {
                    WWText(
                        text = stringResource(R.string.your_assets),
                        style = AppTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Grouped Assets (Using ViewModel calculated sections)
            uiState.sections.forEach { section ->
                item(key = section.type.name) {
                    val titleRes = when(section.type) {
                        AssetType.CRYPTO -> R.string.category_crypto
                        AssetType.US_STOCK -> R.string.category_us_stock
                        AssetType.BIST -> R.string.category_tr_stock
                        AssetType.EXCHANGE -> R.string.category_currency
                        AssetType.COMMODITY -> R.string.category_commodity
                        else -> R.string.other
                    }
                    ExpandablePortfolioSection(
                        title = stringResource(titleRes),
                        expanded = uiState.expandedCategories.contains(section.type),
                        onExpandClick = { viewModel.onEvent(PortfolioUiEvent.OnCategoryClick(section.type)) }
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall)) {
                            section.assets.forEach { asset ->
                                AssetItem(
                                    asset = asset,
                                    onClick = {
                                        viewModel.onEvent(
                                            PortfolioUiEvent.OnAssetClick(
                                                asset.symbol,
                                                asset.type
                                            )
                                        )
                                        onNavigateToDetail(asset.symbol, asset.type)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
