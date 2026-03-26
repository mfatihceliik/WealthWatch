package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.PriceTrend
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.presentation.model.AssetUiModel
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.WealthWatchTheme

@Composable
fun AssetItem(
    asset: AssetUiModel, onClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    val priceTrend = percentColor(asset.trend)

    WWCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Icon
            WWIcon(
                type = asset.type,
                iconUrl = asset.icon
            )

            Spacer(modifier = Modifier.width(spacing.spaceSmall))

            // Symbol & Name
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                WWText(
                    text = asset.symbol,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
                WWText(
                    text = asset.name.ifEmpty { asset.symbol },
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant
                )

                WWText(
                    text = stringResource(R.string.total_amount),
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
            }

            // Price & Change
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                WWText(
                    text = asset.formattedTotalValue,
                    style = AppTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.onSurface
                )
                WWText(
                    text = asset.formattedProfitLoss,
                    style = AppTheme.typography.bodySmall,
                    color = priceTrend,
                    fontWeight = FontWeight.Medium
                )
                WWText(
                    text = asset.formattedAmount,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun AssetItemPreview() {
    WealthWatchTheme {
        AssetItem(
            asset = AssetUiModel(
                symbol = "BTC",
                name = "Bitcoin",
                type = AssetType.CRYPTO,
                formattedTotalValue = "$45,000.00",
                formattedAmount = "1.5",
                formattedProfitLoss = "%5.2",
                isProfit = true,
                trend = PriceTrend.UP
            ),
            onClick = {}
        )
    }
}
