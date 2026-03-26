package com.example.wealthwatch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wealthwatch.R
import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.ui.theme.AppTheme

@Composable
fun WWIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String = "",
    tint: Color = Color.Unspecified
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = if (tint != Color.Unspecified) tint else LocalContentColor.current
    )
}

@Composable
fun WWIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String = "",
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = if (tint != Color.Unspecified) tint else LocalContentColor.current
    )
}

@Composable
fun WWIcon(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String = "",
    placeholder: Painter? = null,
    error: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    tint: Color = Color.Unspecified
) {
    if (url.isNullOrBlank()) {
        if (placeholder != null) {
            Icon(
                painter = placeholder,
                contentDescription = contentDescription,
                modifier = modifier,
                tint = if (tint != Color.Unspecified) tint else LocalContentColor.current
            )
        }
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            placeholder = placeholder,
            error = error,
            colorFilter = if (tint != Color.Unspecified) ColorFilter.tint(tint) else null
        )
    }
}

@Composable
fun WWIcon(
    modifier: Modifier = Modifier,
    type: AssetType,
    iconUrl: String? = null,
    contentDescription: String = ""
) {
    val spacing = AppTheme.spacing
    val placeholderRes = when (type) {
        AssetType.CRYPTO -> R.drawable.crypto_default
        AssetType.US_STOCK, AssetType.TR_STOCK -> R.drawable.stock_default
        AssetType.CURRENCY -> R.drawable.currency_default
        AssetType.COMMODITY -> R.drawable.currency_default
        AssetType.OTHER -> R.drawable.currency_default
        else -> R.drawable.currency_default
    }
    
    val placeholderPainter = painterResource(id = placeholderRes)

    Box(
        modifier = modifier
            .size(spacing.defaultIcon)
            .background(
                color = AppTheme.colors.background,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        WWIcon(
            url = iconUrl,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(spacing.spaceHuge)
                .clip(CircleShape),
            placeholder = placeholderPainter,
            error = placeholderPainter,
            contentScale = ContentScale.Crop
        )
    }
}
