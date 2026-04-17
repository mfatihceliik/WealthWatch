package com.example.wealthwatch.presentation.components.asset

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun AssetIcon(
    modifier: Modifier = Modifier,
    type: AssetType,
    iconUrl: String? = "",
    tint: Color = Color.Unspecified
) {

    val placeholderRes = when (type) {
        AssetType.CRYPTO -> R.drawable.crypto_default
        AssetType.US_STOCK, AssetType.BIST -> R.drawable.stock_default
        AssetType.EXCHANGE -> R.drawable.currency_default
        AssetType.COMMODITY -> R.drawable.currency_default
        AssetType.OTHER -> R.drawable.currency_default
        else -> R.drawable.currency_default
    }

    val placeholderPainter = painterResource(id = placeholderRes)

    if (iconUrl.isNullOrBlank()) {
        Icon(
            painter = placeholderPainter,
            contentDescription = null,
            modifier = modifier
                .size(AppTheme.spacing.defaultIcon)
                .clip(CircleShape),
            tint = if (tint != Color.Unspecified) tint else LocalContentColor.current
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = modifier
                .size(AppTheme.spacing.defaultIcon)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = placeholderPainter,
            error = placeholderPainter,
            colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
        )
    }
}