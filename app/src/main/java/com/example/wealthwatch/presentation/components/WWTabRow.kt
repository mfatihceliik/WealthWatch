package com.example.wealthwatch.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration

import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import com.example.wealthwatch.ui.theme.AppTheme
import com.example.wealthwatch.ui.theme.LocalSpacing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WWTabRow(
    tabs: List<String>,
    pagerState: androidx.compose.foundation.pager.PagerState,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val tabWidth = maxWidth / tabs.size

        SecondaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            divider = {},
            indicator = {
                val indicatorOffset by remember(pagerState) {
                    derivedStateOf {
                        tabWidth * (pagerState.currentPage + pagerState.currentPageOffsetFraction)
                    }
                }

                Box(
                    Modifier
                        .offset(x = indicatorOffset)
                        .width(tabWidth)
                        .fillMaxHeight()
                        .padding(spacing.spaceExtraSmall)
                        .clip(RoundedCornerShape(spacing.containerCornerRadius))
                        .background(AppTheme.colors.primary)
                )
            },
            modifier = Modifier
                .height(spacing.spaceHuge)
                .clip(RoundedCornerShape(spacing.containerCornerRadius))
                .background(AppTheme.colors.surfaceVariant.copy(alpha = 0.3f))
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index

                val textColor by animateColorAsState(
                    targetValue = if (isSelected) AppTheme.colors.onPrimary
                    else AppTheme.colors.onSurfaceVariant,
                    animationSpec = tween(durationMillis = 200),
                    label = "textColor"
                )

                CompositionLocalProvider(LocalRippleConfiguration provides null) {
                    Tab(
                        selected = isSelected,
                        onClick = { onTabClick(index) },
                        modifier = Modifier.zIndex(1f),
                        interactionSource = remember { MutableInteractionSource() },
                        text = {
                            Text(
                                text = title,
                                style = AppTheme.typography.titleSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = textColor
                            )
                        }
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun WWTabRowPreview() {
    com.example.wealthwatch.ui.theme.WealthWatchTheme {
        val pagerState = androidx.compose.foundation.pager.rememberPagerState { 3 }
        WWTabRow(
            tabs = listOf("Tab 1", "Tab 2", "Tab 3"),
            pagerState = pagerState,
            onTabClick = {}
        )
    }
}
